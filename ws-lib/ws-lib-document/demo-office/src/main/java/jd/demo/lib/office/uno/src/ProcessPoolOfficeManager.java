package jd.demo.lib.office.uno.src;


import com.sun.star.beans.XPropertySet;
import com.sun.star.bridge.XBridge;
import com.sun.star.bridge.XBridgeFactory;
import com.sun.star.bridge.XInstanceProvider;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.connection.NoConnectException;
import com.sun.star.connection.XConnection;
import com.sun.star.connection.XConnector;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.*;
import com.sun.star.lang.EventObject;
import com.sun.star.uno.XComponentContext;
import org.artofsolving.jodconverter.office.*;
import org.artofsolving.jodconverter.process.LinuxProcessManager;
import org.artofsolving.jodconverter.process.ProcessManager;
import org.artofsolving.jodconverter.process.ProcessQuery;
import org.artofsolving.jodconverter.process.PureJavaProcessManager;
import org.artofsolving.jodconverter.util.PlatformUtils;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProcessPoolOfficeManager implements OfficeManager {
    public static final long DEFAULT_RETRY_TIMEOUT = 120000L;
    private final int port ;
    private File officeHome = OfficeUtils.getDefaultOfficeHome();
    private OfficeConnectionProtocol connectionProtocol;
    private long taskQueueTimeout = 30000L ;
    private long taskExecutionTimeout= 120000L;
    private int maxTasksPerProcess= 200;
    private long retryTimeout = 120000L;
    private final ProcessManager processManager;

    private final OfficeProcessSettings settings;
    private final ManagedOfficeProcess managedOfficeProcess;
    private final SuspendableThreadPoolExecutor taskExecutor;
    private volatile boolean stopping;
    private int taskCount;
    private Future<?> currentTask;
    private final Logger logger;
    private OfficeConnectionEventListener connectionEventListener;

    public ProcessPoolOfficeManager(int port ) {
        this.port = port ;
        this.processManager = PlatformUtils.isLinux()?new LinuxProcessManager():new PureJavaProcessManager();

        UnoUrl unoUrl = UnoUrl.socket(port);
        settings = new OfficeProcessSettings(unoUrl);
            /*settings.setRunAsArgs(runAsArgs);
            settings.setTemplateProfileDir(templateProfileDir);
            settings.setWorkDir(workDir);*/
        settings.setOfficeHome(officeHome);
        settings.setRetryTimeout(retryTimeout);
        settings.setTaskExecutionTimeout(taskExecutionTimeout);
        settings.setMaxTasksPerProcess(maxTasksPerProcess);
        settings.setProcessManager(processManager);

        this.stopping = false;
        this.logger = Logger.getLogger(this.getClass().getName());
        this.connectionEventListener = new OfficeConnectionEventListener() {
            public void connected(OfficeConnectionEvent event) {
                taskCount = 0;
                taskExecutor.setAvailable(true);
            }

            public void disconnected(OfficeConnectionEvent event) {
                taskExecutor.setAvailable(false);
                if(stopping) {
                    stopping = false;
                } else {
                    logger.warning("connection lost unexpectedly; attempting restart");
                    if(currentTask != null) {
                        currentTask.cancel(true);
                    }
                    managedOfficeProcess.restartDueToLostConnection();
                }

            }
        };
        this.managedOfficeProcess = new ManagedOfficeProcess(settings);
        this.managedOfficeProcess.getConnection().addConnectionEventListener(this.connectionEventListener);
        this.taskExecutor = new SuspendableThreadPoolExecutor(new NamedThreadFactory("OfficeTaskThread"));
    }


    public void execute(final OfficeTask task) throws OfficeException {
        Future futureTask = this.taskExecutor.submit(new Runnable() {
            public void run() {
                if(settings.getMaxTasksPerProcess() > 0 && ++taskCount == settings.getMaxTasksPerProcess() + 1) {
                    logger.info(String.format("reached limit of %d maxTasksPerProcess: restarting",
                            new Object[]{Integer.valueOf(settings.getMaxTasksPerProcess())}));
                    taskExecutor.setAvailable(false);
                    stopping = true;
                    managedOfficeProcess.restartAndWait();
                }
                task.execute(managedOfficeProcess.getConnection());
            }
        });
        this.currentTask = futureTask;

        try {
            futureTask.get(this.settings.getTaskExecutionTimeout(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException var4) {
            this.managedOfficeProcess.restartDueToTaskTimeout();
            throw new OfficeException("task did not complete within timeout", var4);
        } catch (ExecutionException var5) {
            if(var5.getCause() instanceof OfficeException) {
                throw (OfficeException)var5.getCause();
            } else {
                throw new OfficeException("task failed", var5.getCause());
            }
        } catch (Exception var6) {
            throw new OfficeException("task failed", var6);
        }
    }

    public void start() throws OfficeException {
        this.managedOfficeProcess.startAndWait();
    }

    public void stop() throws OfficeException {
        this.taskExecutor.setAvailable(false);
        this.stopping = true;
        this.taskExecutor.shutdownNow();
        this.managedOfficeProcess.stopAndWait();
    }

    public boolean isRunning() {
        return this.managedOfficeProcess.isConnected();
    }
}

class SuspendableThreadPoolExecutor extends ThreadPoolExecutor {
    private boolean available = false;
    private ReentrantLock suspendLock = new ReentrantLock();
    private Condition availableCondition;

    public SuspendableThreadPoolExecutor(ThreadFactory threadFactory) {
        super(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), threadFactory);
        this.availableCondition = this.suspendLock.newCondition();
    }

    protected void beforeExecute(Thread thread, Runnable task) {
        super.beforeExecute(thread, task);
        this.suspendLock.lock();

        try {
            while(!this.available) {
                this.availableCondition.await();
            }
        } catch (InterruptedException var7) {
            thread.interrupt();
        } finally {
            this.suspendLock.unlock();
        }

    }

    public void setAvailable(boolean available) {
        this.suspendLock.lock();

        try {
            this.available = available;
            if(available) {
                this.availableCondition.signalAll();
            }
        } finally {
            this.suspendLock.unlock();
        }

    }
}

class OfficeConnection implements OfficeContext {
    private static AtomicInteger bridgeIndex = new AtomicInteger();
    private final UnoUrl unoUrl;
    private XComponent bridgeComponent;
    private XMultiComponentFactory serviceManager;
    private XComponentContext componentContext;
    private final List<OfficeConnectionEventListener> connectionEventListeners = new ArrayList();
    private volatile boolean connected = false;
    private XEventListener bridgeListener = new XEventListener() {
        public void disposing(EventObject event) {
            if(OfficeConnection.this.connected) {
                OfficeConnection.this.connected = false;
                OfficeConnection.this.logger.info(String.format("disconnected: \'%s\'", new Object[]{OfficeConnection.this.unoUrl}));
                OfficeConnectionEvent connectionEvent = new OfficeConnectionEvent(OfficeConnection.this);
                Iterator i$ = OfficeConnection.this.connectionEventListeners.iterator();

                while(i$.hasNext()) {
                    OfficeConnectionEventListener listener = (OfficeConnectionEventListener)i$.next();
                    listener.disconnected(connectionEvent);
                }
            }

        }
    };
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public OfficeConnection(UnoUrl unoUrl) {
        this.unoUrl = unoUrl;
    }

    public void addConnectionEventListener(OfficeConnectionEventListener connectionEventListener) {
        this.connectionEventListeners.add(connectionEventListener);
    }

    public void connect() throws ConnectException {
        this.logger.fine(String.format("connecting with connectString \'%s\'", new Object[]{this.unoUrl}));

        try {
            XComponentContext exception = Bootstrap.createInitialComponentContext((Hashtable)null);
            XMultiComponentFactory localServiceManager = exception.getServiceManager();
            XConnector connector = (XConnector)OfficeUtils.cast(XConnector.class, localServiceManager.createInstanceWithContext("com.sun.star.connection.Connector", exception));
            XConnection connection = connector.connect(this.unoUrl.getConnectString());
            XBridgeFactory bridgeFactory = (XBridgeFactory)OfficeUtils.cast(XBridgeFactory.class, localServiceManager.createInstanceWithContext("com.sun.star.bridge.BridgeFactory", exception));
            String bridgeName = "jodconverter_" + bridgeIndex.getAndIncrement();
            XBridge bridge = bridgeFactory.createBridge(bridgeName, "urp", connection, (XInstanceProvider)null);
            this.bridgeComponent = (XComponent)OfficeUtils.cast(XComponent.class, bridge);
            this.bridgeComponent.addEventListener(this.bridgeListener);
            this.serviceManager = (XMultiComponentFactory)OfficeUtils.cast(XMultiComponentFactory.class, bridge.getInstance("StarOffice.ServiceManager"));
            XPropertySet properties = (XPropertySet)OfficeUtils.cast(XPropertySet.class, this.serviceManager);
            this.componentContext = (XComponentContext)OfficeUtils.cast(XComponentContext.class, properties.getPropertyValue("DefaultContext"));
            this.connected = true;
            this.logger.info(String.format("connected: \'%s\'", new Object[]{this.unoUrl}));
            OfficeConnectionEvent connectionEvent = new OfficeConnectionEvent(this);
            Iterator i$ = this.connectionEventListeners.iterator();

            while(i$.hasNext()) {
                OfficeConnectionEventListener listener = (OfficeConnectionEventListener)i$.next();
                listener.connected(connectionEvent);
            }

        } catch (NoConnectException var12) {
            throw new ConnectException(String.format("connection failed: \'%s\'; %s", new Object[]{this.unoUrl, var12.getMessage()}));
        } catch (Exception var13) {
            throw new OfficeException("connection failed: " + this.unoUrl, var13);
        }
    }

    public boolean isConnected() {
        return this.connected;
    }

    public synchronized void disconnect() {
        this.logger.fine(String.format("disconnecting: \'%s\'", new Object[]{this.unoUrl}));
        this.bridgeComponent.dispose();
    }

    public Object getService(String serviceName) {
        try {
            return this.serviceManager.createInstanceWithContext(serviceName, this.componentContext);
        } catch (Exception var3) {
            throw new OfficeException(String.format("failed to obtain service \'%s\'", new Object[]{serviceName}), var3);
        }
    }
}
class OfficeConnectionEvent extends java.util.EventObject {
    private static final long serialVersionUID = 2060652797570876077L;

    public OfficeConnectionEvent(OfficeConnection source) {
        super(source);
    }
}

interface OfficeConnectionEventListener extends EventListener {
    void connected(OfficeConnectionEvent var1);

    void disconnected(OfficeConnectionEvent var1);
}

class UnoUrl {
    private final String acceptString;
    private final String connectString;

    private UnoUrl(String acceptString, String connectString) {
        this.acceptString = acceptString;
        this.connectString = connectString;
    }

    public static UnoUrl socket(int port) {
        String socketString = "socket,host=127.0.0.1,port=" + port;
        return new UnoUrl(socketString, socketString + ",tcpNoDelay=1");
    }

    public static UnoUrl pipe(String pipeName) {
        String pipeString = "pipe,name=" + pipeName;
        return new UnoUrl(pipeString, pipeString);
    }

    public String getAcceptString() {
        return this.acceptString;
    }

    public String getConnectString() {
        return this.connectString;
    }

    public String toString() {
        return this.connectString;
    }
}


class ManagedOfficeProcess {

    private static final Integer EXIT_CODE_NEW_INSTALLATION = Integer.valueOf(81);
    private final OfficeProcessSettings settings;
    private final OfficeProcess process;
    private final OfficeConnection connection;
    private ExecutorService executor = Executors.newSingleThreadExecutor(new NamedThreadFactory("OfficeProcessThread"));
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public ManagedOfficeProcess(OfficeProcessSettings settings) throws OfficeException {
        this.settings = settings;
        this.process = new OfficeProcess(settings.getOfficeHome(), settings.getUnoUrl(), settings.getRunAsArgs(), settings.getTemplateProfileDir(), settings.getWorkDir(), settings.getProcessManager());
        this.connection = new OfficeConnection(settings.getUnoUrl());
    }

    public OfficeConnection getConnection() {
        return this.connection;
    }

    public void startAndWait() throws OfficeException {
        Future future = this.executor.submit(new Runnable() {
            public void run() {
                ManagedOfficeProcess.this.doStartProcessAndConnect();
            }
        });

        try {
            future.get();
        } catch (Exception var3) {
            throw new OfficeException("failed to start and connect", var3);
        }
    }

    public void stopAndWait() throws OfficeException {
        Future future = this.executor.submit(new Runnable() {
            public void run() {
                ManagedOfficeProcess.this.doStopProcess();
            }
        });

        try {
            future.get();
        } catch (Exception var3) {
            throw new OfficeException("failed to start and connect", var3);
        }
    }

    public void restartAndWait() {
        Future future = this.executor.submit(new Runnable() {
            public void run() {
                ManagedOfficeProcess.this.doStopProcess();
                ManagedOfficeProcess.this.doStartProcessAndConnect();
            }
        });

        try {
            future.get();
        } catch (Exception var3) {
            throw new OfficeException("failed to restart", var3);
        }
    }

    public void restartDueToTaskTimeout() {
        this.executor.execute(new Runnable() {
            public void run() {
                ManagedOfficeProcess.this.doTerminateProcess();
            }
        });
    }

    public void restartDueToLostConnection() {
        this.executor.execute(new Runnable() {
            public void run() {
                try {
                    ManagedOfficeProcess.this.doEnsureProcessExited();
                    ManagedOfficeProcess.this.doStartProcessAndConnect();
                } catch (OfficeException var2) {
                    ManagedOfficeProcess.this.logger.log(Level.SEVERE, "could not restart process", var2);
                }

            }
        });
    }

    private void doStartProcessAndConnect() throws OfficeException {
        try {
            this.process.start();
            (new Retryable() {
                protected void attempt() throws TemporaryException, Exception {
                    try {
                        ManagedOfficeProcess.this.connection.connect();
                    } catch (ConnectException var3) {
                        Integer exitCode = ManagedOfficeProcess.this.process.getExitCode();
                        if(exitCode == null) {
                            throw new TemporaryException(var3);
                        } else if(exitCode.equals(ManagedOfficeProcess.EXIT_CODE_NEW_INSTALLATION)) {
                            ManagedOfficeProcess.this.logger.log(Level.WARNING, "office process died with exit code 81; restarting it");
                            ManagedOfficeProcess.this.process.start(true);
                            throw new TemporaryException(var3);
                        } else {
                            throw new OfficeException("office process died with exit code " + exitCode);
                        }
                    }
                }
            }).execute(this.settings.getRetryInterval(), this.settings.getRetryTimeout());
        } catch (Exception var2) {
            throw new OfficeException("could not establish connection", var2);
        }
    }

    private void doStopProcess() {
        try {
            XDesktop exception = (XDesktop) OfficeUtils.cast(XDesktop.class, this.connection.getService("com.sun.star.frame.Desktop"));
            exception.terminate();
        } catch (DisposedException var2) {
            ;
        } catch (Exception var3) {
            this.doTerminateProcess();
        }

        this.doEnsureProcessExited();
    }

    private void doEnsureProcessExited() throws OfficeException {
        try {
            int retryTimeoutException = this.process.getExitCode(this.settings.getRetryInterval(), this.settings.getRetryTimeout());
            this.logger.info("process exited with code " + retryTimeoutException);
        } catch (RetryTimeoutException var2) {
            this.doTerminateProcess();
        }

        //this.process.deleteProfileDir();
    }

    private void doTerminateProcess() throws OfficeException {
        try {
            int exception = this.process.forciblyTerminate(this.settings.getRetryInterval(), this.settings.getRetryTimeout());
            this.logger.info("process forcibly terminated with code " + exception);
        } catch (Exception var2) {
            throw new OfficeException("could not terminate process", var2);
        }
    }

    boolean isConnected() {
        return this.connection.isConnected();
    }
}

class OfficeProcess {
    private final File officeHome;
    private final UnoUrl unoUrl;
    private final String[] runAsArgs;
    private final ProcessManager processManager;
    private Process process;
    private long pid = -1L;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public OfficeProcess(File officeHome, UnoUrl unoUrl, String[] runAsArgs, File templateProfileDir, File workDir, ProcessManager processManager) {
        this.officeHome = officeHome;
        this.unoUrl = unoUrl;
        this.runAsArgs = runAsArgs;
        this.processManager = processManager;
    }

    public void start() throws IOException {
        this.start(false);
    }

    public void start(boolean restart) throws IOException {
        ProcessQuery processQuery = new ProcessQuery("soffice", this.unoUrl.getAcceptString());
        long existingPid = this.processManager.findPid(processQuery);
        if(existingPid != -2L && existingPid != -1L) {
            throw new IllegalStateException(String.format("a process with acceptString \'%s\' is already running; pid %d", new Object[]{this.unoUrl.getAcceptString(), Long.valueOf(existingPid)}));
        } else {
            ArrayList command = new ArrayList();
            //File executable = OfficeUtils.getOfficeExecutable(this.officeHome);

            command.add("soffice");
            command.add("-accept=" + this.unoUrl.getAcceptString() + ";urp;");
            //command.add("-env:UserInstallation=" + OfficeUtils.toUrl(this.instanceProfileDir));
            command.add("-headless");
            command.add("-nodefault");
            command.add("-nofirststartwizard");
            command.add("-nolockcheck");
            command.add("-nologo");
            command.add("-norestore");
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            this.logger.info("starting process with acceptString {}" + unoUrl.getAcceptString());
            this.process = processBuilder.start();
            this.pid = this.processManager.findPid(processQuery);
            if(this.pid == -2L) {
                throw new IllegalStateException(String.format("process with acceptString \'%s\' started but its pid could not be found", new Object[]{this.unoUrl.getAcceptString()}));
            } else {
                this.logger.info("started process" + (this.pid != -1L?"; pid = " + this.pid:""));
            }
        }
    }

    public boolean isRunning() {
        return this.process == null?false:this.getExitCode() == null;
    }

    public Integer getExitCode() {
        try {
            return Integer.valueOf(this.process.exitValue());
        } catch (IllegalThreadStateException var2) {
            return null;
        }
    }

    public int getExitCode(long retryInterval, long retryTimeout) throws RetryTimeoutException {
        try {
            ExitCodeRetryable exception = new ExitCodeRetryable();
            exception.execute(retryInterval, retryTimeout);
            return exception.getExitCode();
        } catch (RetryTimeoutException var6) {
            throw var6;
        } catch (Exception var7) {
            throw new OfficeException("could not get process exit code", var7);
        }
    }

    public int forciblyTerminate(long retryInterval, long retryTimeout) throws IOException, RetryTimeoutException {
        this.logger.info(String.format("trying to forcibly terminate process: \'" + this.unoUrl + "\'" + (this.pid != -1L?" (pid " + this.pid + ")":""), new Object[0]));
        this.processManager.kill(this.process, this.pid);
        return this.getExitCode(retryInterval, retryTimeout);
    }

    private class ExitCodeRetryable extends Retryable {
        private int exitCode;

        private ExitCodeRetryable() {
        }

        protected void attempt() throws TemporaryException, Exception {
            try {
                this.exitCode = OfficeProcess.this.process.exitValue();
            } catch (IllegalThreadStateException var2) {
                throw new TemporaryException(var2);
            }
        }

        public int getExitCode() {
            return this.exitCode;
        }
    }
}

abstract class Retryable {

    protected abstract void attempt() throws TemporaryException, Exception;

    public void execute(long interval, long timeout) throws RetryTimeoutException, Exception {
        this.execute(0L, interval, timeout);
    }

    public void execute(long delay, long interval, long timeout) throws RetryTimeoutException, Exception {
        long start = System.currentTimeMillis();
        if(delay > 0L) {
            this.sleep(delay);
        }
        while(true) {
            try {
                this.attempt();
                return;
            } catch (TemporaryException var10) {
                if(System.currentTimeMillis() - start >= timeout) {
                    throw new RetryTimeoutException(var10.getCause());
                }

                this.sleep(interval);
            }
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException var4) {}
    }
}

class TemporaryException extends Exception {
    private static final long serialVersionUID = 7237380113208327295L;
    public TemporaryException(Throwable cause) {
        super(cause);
    }
}


class OfficeProcessSettings {
    public static final long DEFAULT_RETRY_INTERVAL = 250L;
    private final UnoUrl unoUrl;
    private File officeHome = OfficeUtils.getDefaultOfficeHome();
    private String[] runAsArgs;
    private File templateProfileDir;
    private File workDir = new File(System.getProperty("java.io.tmpdir"));
    private ProcessManager processManager = new PureJavaProcessManager();
    private long retryTimeout = 120000L;
    private long retryInterval = 250L;

    public static final long DEFAULT_TASK_EXECUTION_TIMEOUT = 120000L;
    public static final int DEFAULT_MAX_TASKS_PER_PROCESS = 200;
    private long taskExecutionTimeout = 120000L;
    private int maxTasksPerProcess = 200;

    public OfficeProcessSettings(UnoUrl unoUrl) {
        this.unoUrl = unoUrl;
    }

    public long getTaskExecutionTimeout() {
        return this.taskExecutionTimeout;
    }

    public void setTaskExecutionTimeout(long taskExecutionTimeout) {
        this.taskExecutionTimeout = taskExecutionTimeout;
    }

    public int getMaxTasksPerProcess() {
        return this.maxTasksPerProcess;
    }

    public void setMaxTasksPerProcess(int maxTasksPerProcess) {
        this.maxTasksPerProcess = maxTasksPerProcess;
    }


    public UnoUrl getUnoUrl() {
        return this.unoUrl;
    }

    public File getOfficeHome() {
        return this.officeHome;
    }

    public void setOfficeHome(File officeHome) {
        this.officeHome = officeHome;
    }

    public String[] getRunAsArgs() {
        return this.runAsArgs;
    }

    public void setRunAsArgs(String[] runAsArgs) {
        this.runAsArgs = runAsArgs;
    }

    public File getTemplateProfileDir() {
        return this.templateProfileDir;
    }

    public void setTemplateProfileDir(File templateProfileDir) {
        this.templateProfileDir = templateProfileDir;
    }

    public File getWorkDir() {
        return this.workDir;
    }

    public void setWorkDir(File workDir) {
        this.workDir = workDir;
    }

    public ProcessManager getProcessManager() {
        return this.processManager;
    }

    public void setProcessManager(ProcessManager processManager) {
        this.processManager = processManager;
    }

    public long getRetryTimeout() {
        return this.retryTimeout;
    }

    public void setRetryTimeout(long retryTimeout) {
        this.retryTimeout = retryTimeout;
    }

    public long getRetryInterval() {
        return this.retryInterval;
    }

    public void setRetryInterval(long retryInterval) {
        this.retryInterval = retryInterval;
    }
}


class RetryTimeoutException extends Exception {
    private static final long serialVersionUID = -3704437769955257514L;

    public RetryTimeoutException(Throwable cause) {
        super(cause);
    }
}

class NamedThreadFactory implements ThreadFactory {
    private static final AtomicInteger threadIndex = new AtomicInteger(0);
    private final String baseName;
    private final boolean daemon;

    public NamedThreadFactory(String baseName) {
        this(baseName, true);
    }

    public NamedThreadFactory(String baseName, boolean daemon) {
        this.baseName = baseName;
        this.daemon = daemon;
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, this.baseName + "-" + threadIndex.getAndIncrement());
        thread.setDaemon(this.daemon);
        return thread;
    }
}




