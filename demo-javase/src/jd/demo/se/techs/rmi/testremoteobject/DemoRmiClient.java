package jd.demo.se.techs.rmi.testremoteobject;

import jd.demo.se.techs.rmi.Task;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class DemoRmiClient {

	static {
		DemoRmiServer.setEnv();
	}
	
	public static void main(String[] args) throws AccessException, RemoteException, NotBoundException {
		// For RMI to download classes, a security manager must be in force.
		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		Compute rs = (Compute)LocateRegistry.getRegistry(DemoRmiServer.PORT).lookup(TestRemoteObject.NAME);
		/*Properties result = rs.executeTask(()->System.getProperties());
		result.list(System.out);*/
		rs.inc();
		Task<String> task = ()->rs.cntFormat();
		System.out.println("volume="+ rs.executeTask(task));
	}

}
