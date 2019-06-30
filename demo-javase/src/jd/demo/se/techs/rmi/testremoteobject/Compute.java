package jd.demo.se.techs.rmi.testremoteobject;

import jd.demo.se.techs.rmi.Task;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Compute extends Remote {

	public <T> T executeTask(Task<T> t) throws RemoteException;
	
	public void inc();
	
	public String cntFormat();
}