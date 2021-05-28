package com.fourinone.pattern.worker;

import com.fourinone.*;
import com.fourinone.delegate.Delegate;
import com.fourinone.delegate.DelegatePolicy;
import com.fourinone.pattern.warehouse.WareHouse;
import com.fourinone.util.LogUtil;

public class WorkerLocalProxy extends PoolExector
{
	MigrantWorker mwobj;
	Thread mwThread;
	private RecallException rx;
	
	public WorkerLocalProxy(){
		rx = new RecallException();
	}
	
	@Delegate(interfaceName=WorkerLocal.class,methodName="setWorker",policy= DelegatePolicy.Implements)
	public void setWorkerObject(MigrantWorker mwobj){
		this.mwobj = mwobj;
	}
	
	@Delegate(interfaceName=WorkerLocal.class,methodName="interrupt",policy=DelegatePolicy.Implements)
	public void cancel(){
		try{
			mwobj.interrupted(true);
         	mwThread.interrupt();
		}catch(Exception e){
			LogUtil.info("Interrupt", "exception", e);
		}
	}
	
	@Delegate(interfaceName=WorkerLocal.class,methodName="getHost",policy=DelegatePolicy.Implements)
	public String getHost(){
		return null;
	}
	
	@Delegate(interfaceName=WorkerLocal.class,methodName="getPort",policy=DelegatePolicy.Implements)
	public int getPort(){
		return -1;
	}
	
	@Delegate(interfaceName=WorkerLocal.class,methodName="doTask",policy=DelegatePolicy.Implements)
	public WareHouse doTaskLocalProxy(WareHouse inhouse){
		return doTaskLocalProxy(inhouse, 0);
	}
	
	@Delegate(interfaceName=WorkerLocal.class,methodName="doTask",policy=DelegatePolicy.Implements)
	public WareHouse doTaskLocalProxy(final WareHouse inhouse, long t)
	{
		if(rx.tryRecall(inhouse)==-1)
			return null;
			
		final WareHouse outhouse = new WareHouse(false);
		execute(new Runnable(){
			public void run(){
				try{
					mwThread = Thread.currentThread();
					WareHouse wh = mwobj.doTask(inhouse);
					if(wh!=null){
						rx.setRecall(false);
						outhouse.putAll(wh);
					}
					outhouse.setReady(FileResult.READY);
				}catch(Exception e){
					LogUtil.info("doTaskLocalProxy", "exception", e);
					rx.setRecall(false);
					outhouse.setReady(FileResult.EXCEPTION);
				}
			}
		},new Runnable(){
             public void run(){
             	cancel();
             }
        },t);
		
		return outhouse;
	}
}