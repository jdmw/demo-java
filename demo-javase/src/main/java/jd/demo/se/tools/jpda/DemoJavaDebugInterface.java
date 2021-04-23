package jd.demo.se.tools.jpda;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import jd.util.lang.concurrent.CcUt;

import java.io.IOException;
import java.util.*;


public class DemoJavaDebugInterface {

    private final static String LOG_HEADER = "[JDI]" ;

    // 中断点 map<className,lines>
    private final Map<String,List<Integer>> breakPointLines ;
    private boolean vmExit = false;

    public static void main(String[] args) throws Exception {
        debug(jd.demo.se.tools.jpda.Hello.class,Arrays.asList(7));
    }

    public static void debug(Class clazz,List<Integer> breakPointLines) throws Exception{
        Map<String,List<Integer>>  classLines = new HashMap<>();
        classLines.putIfAbsent(clazz.getName(),breakPointLines);
        new DemoJavaDebugInterface(clazz,classLines);
    }
    protected DemoJavaDebugInterface(Class clazz,Map<String,List<Integer>>  breakPointLines) throws Exception{

        this(clazz.getName(),clazz.getResource("/").getFile(),breakPointLines);
    }
    protected DemoJavaDebugInterface(String mainClassName,String classPath,Map<String,List<Integer>> breakPointLines) throws Exception {
        this.breakPointLines = breakPointLines ;

        VirtualMachineManager virtualMachineManager = Bootstrap.virtualMachineManager();
        LaunchingConnector launchingConnector = virtualMachineManager.defaultConnector();
        // Get arguments of the launching connector
        Map<String, Connector.Argument> defaultArguments = launchingConnector.defaultArguments();
        Connector.Argument mainArg = defaultArguments.get("main");
        Connector.Argument suspendArg = defaultArguments.get("suspend");
        Connector.Argument options = defaultArguments.get("options");

        // Set class of main method
        mainArg.setValue(mainClassName);
        suspendArg.setValue("true");
        // java options
        options.setValue("-classpath " + classPath);

        VirtualMachine vm = launchingConnector.launch(defaultArguments);
        Process process = vm.process();

        // Register ClassPrepareRequest
        EventRequestManager eventRequestManager = vm.eventRequestManager();
        ClassPrepareRequest classPrepareRequest = eventRequestManager.createClassPrepareRequest();
        classPrepareRequest.addClassFilter(mainClassName);
        classPrepareRequest.addCountFilter(1);
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        classPrepareRequest.enable();

        CcUt.start(()->{
            int i ;
            try {
                while ((i =process.getInputStream().read()) > 0 ){
                    System.out.write(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        CcUt.start(()->{
            int i ;
            try {
                while ((i =process.getErrorStream().read()) > 0 ){
                    System.err.write(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Enter event loop
        this.eventLoop(vm);
        process.destroy();
    }


    private void eventLoop(VirtualMachine vm) throws Exception {

        EventQueue eventQueue = vm.eventQueue();
        while (true) {
            if (vmExit == true) {
                break;
            }
            EventSet eventSet = eventQueue.remove();
            EventIterator eventIterator = eventSet.eventIterator();
            while (eventIterator.hasNext()) {
                Event event = (Event) eventIterator.next();
                execute(vm.eventRequestManager(),eventSet,event);
            }
        }
    }

    private void execute(EventRequestManager eventRequestManager,EventSet eventSet,Event event) throws Exception {
        if (event instanceof VMStartEvent) {
            System.out.println(LOG_HEADER+"VM started");
            eventSet.resume();
        } else if (event instanceof ClassPrepareEvent) {
            ClassPrepareEvent classPrepareEvent = (ClassPrepareEvent) event;
            String className = classPrepareEvent.referenceType().name();
            System.out.println(LOG_HEADER+"Class " + className + " is already prepared");
            if(breakPointLines.containsKey(className)){
                List<Integer> lines = breakPointLines.get(className);
                if(lines != null && !lines.isEmpty()){

                    // Get location
                    ReferenceType referenceType = classPrepareEvent.referenceType();
                    for (Integer line : lines) {
                        List locations = referenceType.locationsOfLine(line);
                        Location location = (Location) locations.get(0);
                        // Create BreakpointEvent
                        BreakpointRequest breakpointRequest = eventRequestManager.createBreakpointRequest(location);
                        breakpointRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
                        breakpointRequest.enable();
                        System.out.println(LOG_HEADER + "Add break point at " + className + ", line " + line);
                    }
                }
            }
            eventSet.resume();
        } else if (event instanceof BreakpointEvent) {
            BreakpointEvent breakpointEvent = (BreakpointEvent) event;
            Location location = breakpointEvent.location();
            System.out.println();
            System.out.println(LOG_HEADER+ "Suspend at " + location.method() + " Line: " + location.lineNumber() );
            ThreadReference threadReference = breakpointEvent.thread();
            StackFrame stackFrame = threadReference.frame(0);

            System.out.println(LOG_HEADER+"Thread: " + stackFrame.thread().name());
            for (LocalVariable localVariable : stackFrame.visibleVariables()) {
                Value value = stackFrame.getValue(localVariable);
                //String str = ((StringReference) value).value();
                System.out.println(LOG_HEADER+"local variable : " + localVariable.name() +" : " + Objects.toString(value));
            }

            eventSet.resume();
        } else if (event instanceof VMDisconnectEvent) {
            vmExit = true;
        } else {
            eventSet.resume();
        }

    }


}
