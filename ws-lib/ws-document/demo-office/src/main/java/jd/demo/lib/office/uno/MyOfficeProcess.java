/*package jd.demo.lib.office.uno;

import com.sun.star.frame.XDesktop;
import com.sun.star.lang.DisposedException;
import org.apache.commons.io.FileUtils;
import org.artofsolving.jodconverter.office.*;
import org.artofsolving.jodconverter.process.*;
import org.artofsolving.jodconverter.util.PlatformUtils;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyOfficeProcess implements OfficeManager {

    int UNO_PORT = 8100 ;
    String UNO_ACCEPT_STRING = "socket,host=127.0.0.1,port=8100,tcpNoDelay=1" ;

    private Process process;
    private long pid = -1L;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public static final long DEFAULT_RETRY_TIMEOUT = 120000L;
    public static final int[] portNumbers= new int[]{2002};
    public static final String[] pipeNames = new String[]{"office"};
    public static final long taskQueueTimeout = 30000L;
    public static final long taskExecutionTimeout  = 120000L;
    public static final int maxTasksPerProcess = 200;
    public static final int retryInterval = 3 ;
    public static final long retryTimeout  = 120000L;
    public static final ProcessManager processManager = (PlatformUtils.isLinux())?new LinuxProcessManager(): new PureJavaProcessManager();

    public void start() throws OfficeException {
        ProcessQuery processQuery = new ProcessQuery("soffice",UNO_ACCEPT_STRING);
        long existingPid = processManager.findPid(processQuery);
        if(existingPid != -2L && existingPid != -1L) {
            logger.info(String.format("a process with acceptString {} is already running; pid {}",
                    UNO_ACCEPT_STRING, Long.valueOf(existingPid)));
        } else {
            ArrayList command = new ArrayList();

            command.add("soffice");
            command.add("-accept=" + UNO_ACCEPT_STRING + ";urp;");
            //command.add("-env:UserInstallation=" + OfficeUtils.toUrl(this.instanceProfileDir));
            command.add("-headless");
            command.add("-nodefault");
            command.add("-nofirststartwizard");
            command.add("-nolockcheck");
            command.add("-nologo");
            command.add("-norestore");
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            this.logger.info(String.format("starting process with acceptString \'%s\'", UNO_ACCEPT_STRING));
            this.process = processBuilder.start();
            this.pid = this.processManager.findPid(processQuery);
            if(this.pid == -2L) {
                throw new IllegalStateException(String.format("process with acceptString \'%s\' started but its pid could not be found",
                        new Object[]{UNO_ACCEPT_STRING}));
            } else {
                this.logger.info("started process" + (this.pid != -1L?"; pid = " + this.pid:""));
            }
        }
    }

    @Override
    public void stop() throws OfficeException {

    }

    public void execute(OfficeTask task) throws IllegalStateException, OfficeException {
        task.execute();
    }

    static class OfficeProcess {
        private Process process;
        private long pid = -1L;
        private final Logger logger = Logger.getLogger(this.getClass().getName());

        private File getInstanceProfileDir(File workDir, UnoUrl unoUrl) {
            String dirName = ".jodconverter_" + unoUrl.getAcceptString().replace(',', '_').replace('=', '-');
            return new File(workDir, dirName);
        }

        private void prepareInstanceProfileDir() throws OfficeException {
            if(this.instanceProfileDir.exists()) {
                this.logger.warning(String.format("profile dir \'%s\' already exists; deleting", new Object[]{this.instanceProfileDir}));
                this.deleteProfileDir();
            }

            if(this.templateProfileDir != null) {
                try {
                    FileUtils.copyDirectory(this.templateProfileDir, this.instanceProfileDir);
                } catch (IOException var2) {
                    throw new OfficeException("failed to create profileDir", var2);
                }
            }
        }

        public void deleteProfileDir() {
            if(this.instanceProfileDir != null) {
                try {
                    FileUtils.deleteDirectory(this.instanceProfileDir);
                } catch (IOException var3) {
                    File oldProfileDir = new File(this.instanceProfileDir.getParentFile(), this.instanceProfileDir.getName() + ".old." + System.currentTimeMillis());
                    if(this.instanceProfileDir.renameTo(oldProfileDir)) {
                        this.logger.warning("could not delete profileDir: " + var3.getMessage() + "; renamed it to " + oldProfileDir);
                    } else {
                        this.logger.severe("could not delete profileDir: " + var3.getMessage());
                    }
                }
            }

        }

        private void addBasisAndUrePaths(ProcessBuilder processBuilder) throws IOException {
            File basisLink = new File(this.officeHome, "basis-link");
            if(!basisLink.isFile()) {
                this.logger.fine("no %OFFICE_HOME%/basis-link found; assuming it\'s OOo 2.x and we don\'t need to append URE and Basic paths");
            } else {
                String basisLinkText = FileUtils.readFileToString(basisLink).trim();
                File basisHome = new File(this.officeHome, basisLinkText);
                File basisProgram = new File(basisHome, "program");
                File ureLink = new File(basisHome, "ure-link");
                String ureLinkText = FileUtils.readFileToString(ureLink).trim();
                File ureHome = new File(basisHome, ureLinkText);
                File ureBin = new File(ureHome, "bin");
                Map environment = processBuilder.environment();
                String pathKey = "PATH";
                Iterator path = environment.keySet().iterator();

                while(path.hasNext()) {
                    String key = (String)path.next();
                    if("PATH".equalsIgnoreCase(key)) {
                        pathKey = key;
                    }
                }

                String path1 = (String)environment.get(pathKey) + ";" + ureBin.getAbsolutePath() + ";" + basisProgram.getAbsolutePath();
                this.logger.fine(String.format("setting %s to \"%s\"", new Object[]{pathKey, path1}));
                environment.put(pathKey, path1);
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
                org.artofsolving.jodconverter.office.OfficeProcess.ExitCodeRetryable exception = new org.artofsolving.jodconverter.office.OfficeProcess.ExitCodeRetryable();
                exception.execute(retryInterval, retryTimeout);
                return exception.getExitCode();
            } catch (RetryTimeoutException var6) {
                throw var6;
            } catch (Exception var7) {
                throw new OfficeException("could not get process exit code", var7);
            }
        }


        private class ExitCodeRetryable extends Retryable {
            private int exitCode;

            protected void attempt() throws TemporaryException, Exception {
                try {
                    this.exitCode = org.artofsolving.jodconverter.office.OfficeProcess.this.process.exitValue();
                } catch (IllegalThreadStateException var2) {
                    throw new TemporaryException(var2);
                }
            }

            public int getExitCode() {
                return this.exitCode;
            }
        }
    }


    static class ManagedOfficeProcess {
        private static final Integer EXIT_CODE_NEW_INSTALLATION = Integer.valueOf(81);
        private final ManagedOfficeProcessSettings settings;
        private final OfficeProcess process;
        private final OfficeConnection connection;
        private ExecutorService executor = Executors.newSingleThreadExecutor(new NamedThreadFactory("OfficeProcessThread"));
        private final Logger logger = Logger.getLogger(this.getClass().getName());

        public ManagedOfficeProcess(ManagedOfficeProcessSettings settings) throws OfficeException {
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
                    doStartProcessAndConnect();
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
                    doStopProcess();
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
                    doStopProcess();
                    doStartProcessAndConnect();
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
                    doTerminateProcess();
                }
            });
        }

        public void restartDueToLostConnection() {
            this.executor.execute(new Runnable() {
                public void run() {
                    try {
                        doEnsureProcessExited();
                        doStartProcessAndConnect();
                    } catch (OfficeException var2) {
                        logger.log(Level.SEVERE, "could not restart process", var2);
                    }
                }
            });
        }

        private void doStartProcessAndConnect() throws OfficeException {
            try {
                this.process.start();
                (new Retryable() {
                    protected void attempt() Exception {
                        try {
                            connection.connect();
                        } catch (ConnectException var3) {
                            Integer exitCode = process.getExitCode();
                            if(exitCode == null) {
                                throw new TemporaryException(var3);
                            } else if(exitCode.equals(EXIT_CODE_NEW_INSTALLATION)) {
                                logger.log(Level.WARNING, "office process died with exit code 81; restarting it");
                                process.start(true);
                                throw new TemporaryException(var3);
                            } else {
                                throw new OfficeException("office process died with exit code " + exitCode);
                            }
                        }
                    }
                }).execute(retryInterval, retryTimeout);
            } catch (Exception var2) {
                throw new OfficeException("could not establish connection", var2);
            }
        }

        private void doStopProcess() {
            try {
                XDesktop exception = (XDesktop)OfficeUtils.cast(XDesktop.class, this.connection.getService("com.sun.star.frame.Desktop"));
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

            this.process.deleteProfileDir();
        }

        private void doTerminateProcess() throws OfficeException {
            try {
                this.logger.info(String.format("trying to forcibly terminate process: \'" + UNO_ACCEPT_STRING+ "\'" + (this.pid != -1L?" (pid " + this.pid + ")":""), new Object[0]));
                this.processManager.kill(this.process, this.pid);
                int exception = process.exitValue();
                this.logger.info("process forcibly terminated with code " + exception);
            } catch (Exception var2) {
                throw new OfficeException("could not terminate process", var2);
            }
        }

        boolean isConnected() {
            return this.connection.isConnected();
        }
    }

}
*/