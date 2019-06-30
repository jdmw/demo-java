package jd.demo.se.techs.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;


public class DemoRmiClient {

	public static void main(String[] args) throws AccessException, RemoteException, NotBoundException {
		// For RMI to download classes, a security manager must be in force.
		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		Compute rs = (Compute)LocateRegistry.getRegistry(DemoRmiServer.PORT).lookup(ComputeEngine.NAME);
		/*Properties result = rs.executeTask(()->System.getProperties());
		result.list(System.out);*/
		
		Task<Number> task = new Task<Number>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2238803408560415771L;
			int length = 100 ;
			int width = 20 ;
			int height = 10 ;
			@Override
			public Number execute() {
				return length * width * height;
			}
			
		};
		System.out.println("volume="+ rs.executeTask(task));
	}

}
