package com.fourinone.pattern.worker;

import com.fourinone.*;
import com.fourinone.delegate.Delegate;
import com.fourinone.delegate.DelegatePolicy;
import com.fourinone.pattern.warehouse.WareHouse;
import com.fourinone.pattern.park.ParkObjValue;
import com.fourinone.pattern.ParkPatternBean;
import com.fourinone.pattern.ParkPatternExector;

final public class WorkerParkProxy
{
	private String domainnodekey;
	private RecallException rx;
	
	public WorkerParkProxy(String domainnodekey)
	{
		this.domainnodekey = domainnodekey;
		rx = new RecallException();
	}
	
	@Delegate(interfaceName=WorkerLocal.class,methodName="doTask",policy= DelegatePolicy.Implements)
	public WareHouse doTaskParkProxy(WareHouse inhouse){
		if(rx.tryRecall(inhouse)==-1)
			return null;
		
		//System.out.println("doTaskParkProxy:"+inhouse);
		WareHouse outhouse = new WareHouse(false);
		//domain,node,inhouse,outhouse->ParkPatternBean->ParkPatternExector to park and get whLastest
		String[] keyarr = ParkObjValue.getDomainNode(domainnodekey);
		ParkPatternBean ppb = new ParkPatternBean(keyarr[0],keyarr[1],inhouse,outhouse,rx);
		ParkPatternExector.append(ppb);
		return outhouse;
	}
}