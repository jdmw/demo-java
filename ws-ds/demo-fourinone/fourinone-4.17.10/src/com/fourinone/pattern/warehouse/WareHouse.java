package com.fourinone.pattern.warehouse;

//import java.io.Serializable;

public class WareHouse extends ObjValue {
	public final static int NOTREADY=1,READY=0,EXCEPTION=-1;
	protected int status=READY;
	//private Exception expt = null;
	private boolean ready = true;
	private boolean mark = true;
	
	public WareHouse(){}
	
	public WareHouse(boolean ready){
		this.ready = ready;
		status = NOTREADY;
	}
	
	public WareHouse(Object k, Object v) {
		this.put(k,v);
	}
	
	/*void setStatus(int status){
		this.status = status;
	}*/
	
	public int getStatus()//synchronized 2014.12.8
	{
		return status;
	}
	
	public String getStatusName()
	{
		String[] statusName = new String[]{"EXCEPTION","READY","NOTREADY"};
		return statusName[status+1];
	}

	
	public synchronized void setReady(int status)//, Exception expt
	{
		this.ready = true;
		this.status = status;
	}
	
	public synchronized boolean isReady()//throws Throwable
	{
		return ready;
	}
	
	public void setMark(boolean mark)
	{
		this.mark = mark;
	}
	
	public boolean getMark()
	{
		return mark;
	}

	public static void main(String[] args)
	{
		WareHouse wh = new WareHouse("key",new java.util.ArrayList());
		//wh.put("bbb",new Bean("",99,new java.util.ArrayList()));
		System.out.println(wh.get("key"));
	}
}
