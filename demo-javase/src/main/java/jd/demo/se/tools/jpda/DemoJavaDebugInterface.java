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
import java.util.List;
import java.util.Map;


public class DemoJavaDebugInterface {
    static VirtualMachine vm;
    static Process process;
    static EventRequestManager eventRequestManager;
    static EventQueue eventQueue;
    static EventSet eventSet;
    static boolean vmExit = false;

    static String CLASS_NAME = jd.demo.se.tools.jpda.Hello.class.getName();

    public static void main(String[] args) throws Exception {
        LaunchingConnector launchingConnector = Bootstrap.virtualMachineManager().defaultConnector();
        // Get arguments of the launching connector
        Map<String, Connector.Argument> defaultArguments = launchingConnector.defaultArguments();
        Connector.Argument mainArg = defaultArguments.get("main");
        Connector.Argument suspendArg = defaultArguments.get("suspend");

        // Set class of main method
        mainArg.setValue(CLASS_NAME);
        suspendArg.setValue("true");
        vm = launchingConnector.launch(defaultArguments);
        process = vm.process();

        // Register ClassPrepareRequest
        eventRequestManager = vm.eventRequestManager();
        ClassPrepareRequest classPrepareRequest = eventRequestManager.createClassPrepareRequest();
        classPrepareRequest.addClassFilter(CLASS_NAME);
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
        eventLoop();
        process.destroy();



    }


    private static void eventLoop() throws Exception {

        eventQueue = vm.eventQueue();
        while (true) {
            if (vmExit == true) {
                break;
            }
            eventSet = eventQueue.remove();
            EventIterator eventIterator = eventSet.eventIterator();
            while (eventIterator.hasNext()) {
                Event event = (Event) eventIterator.next();
                execute(event);
            }
        }
    }

    private static void execute(Event event) throws Exception {
        if (event instanceof VMStartEvent) {
            System.out.println("VM started");
            eventSet.resume();
        } else if (event instanceof ClassPrepareEvent) {
            ClassPrepareEvent classPrepareEvent = (ClassPrepareEvent) event;
            String mainClassName = classPrepareEvent.referenceType().name();

            if (mainClassName.equals(CLASS_NAME)) {
                System.out.println("Class " + mainClassName + " is already prepared");
                // Get location
                ReferenceType referenceType = classPrepareEvent.referenceType();
                List locations = referenceType.locationsOfLine(7);
                Location location = (Location) locations.get(0);
                // Create BreakpointEvent
                BreakpointRequest breakpointRequest = eventRequestManager
                        .createBreakpointRequest(location);
                breakpointRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
                breakpointRequest.enable();
            }
            eventSet.resume();

        } else if (event instanceof BreakpointEvent) {
            System.out.println("Reach line 10 of " + CLASS_NAME);
            BreakpointEvent breakpointEvent = (BreakpointEvent) event;
            ThreadReference threadReference = breakpointEvent.thread();
            StackFrame stackFrame = threadReference.frame(0);
            LocalVariable localVariable = stackFrame.visibleVariableByName("str");
            Value value = stackFrame.getValue(localVariable);
            String str = ((StringReference) value).value();
            System.out.println("The local variable str at line 10 is " + str + " of " + value.type().name());
            eventSet.resume();
        } else if (event instanceof VMDisconnectEvent) {
            vmExit = true;
        } else {
            eventSet.resume();
        }

    }


}
