package jd.demo.se.techs.rmi.testremoteobject;

import jd.demo.se.techs.rmi.Task;

import java.rmi.RemoteException;

public class TestRemoteObject implements Compute {

	public static final String NAME = "TestRemoteObject" ;

	private int instentCnt ;
	private static int staticCnt ;
	
	public void inc() {
		instentCnt++;
		staticCnt++;
		System.out.println(cntFormat());
	}
	
	public String cntFormat() {
		return "cnt = " + instentCnt + "|" + staticCnt ;
	}
	
	public <T> T executeTask(Task<T> t) throws RemoteException {
		inc();
		System.out.println("client:"+t.execute());
		System.out.println("server:"+cntFormat());
		return (T)cntFormat();
	}

}
