package jd.demo.se.techs.rmi;


import jd.demo.se.techs.rmi.testremoteobject.TestRemoteObject;

import java.net.URISyntaxException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Permission;


public class DemoRmiServer {
    protected static final int PORT = 5099 ;
    static {
        setEnv();
    }


    public static void main(String[] args) throws RemoteException, URISyntaxException, AlreadyBoundException {
        //System.out.println(DemoRmiServer.class.getClassLoader().getResource("jd/demo/se/techs/rmi/rmi.policy").toURI());


        System.out.println("java.rmi.server.codebase: " + System.getProperty("java.rmi.server.codebase"));
        System.out.println("java.rmi.server.hostname: " + System.getProperty("java.rmi.server.hostname"));
        System.out.println("java.security.policy:	  " + System.getProperty("java.security.policy"));


        // create and install a security manager
        // or append jvm parameter -Djava.security.manager
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        // Create and export one or the remote objects
        TestRemoteObject rmiObject = new TestRemoteObject();
        Compute stub = (Compute) UnicastRemoteObject.exportObject(rmiObject, 0);

        LocateRegistry.createRegistry(PORT).rebind(TestRemoteObject.NAME, stub);
    }


    public static class MySecurityManager extends SecurityManager {
        public void checkPermission(Permission perm) {
            System.out.println("perm=" + perm);
            super.checkPermission(perm);
        }
    }

    public static void setEnv() {
        System.setProperty("java.rmi.server.codebase","file:/d:/work/java/demo/demo-javase/src/jd/demo/se/techs/rmi/rmi.jar");
        System.setProperty("java.rmi.server.hostname","localhost"); // can't use localhost or 127.0.0.1 or 192.168.80.2
        System.setProperty("java.security.policy","/work/java/demo/demo-javase/src/jd/demo/se/techs/rmi/rmi.policy");
    }

}
