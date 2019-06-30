package jd.demo.se.techs.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.function.Supplier;

public interface Compute extends Remote {

	public <T> T executeTask(Task<T> t) throws RemoteException;
	
	
}
