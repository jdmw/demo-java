package com.fourinone.pattern;

import com.fourinone.pattern.warehouse.WareHouse;
import com.fourinone.pattern.worker.MigrantWorker;

class ContractorService extends MigrantWorker {
	private ContractorParallel ctor = null;
	ContractorService(ContractorParallel ctor){
		this.ctor = ctor;
	}
	
	public WareHouse doTask(WareHouse inhouse){
		return ctor.giveTask(inhouse);
	}
	
	/*void waitWorking(String host, int port, String workerType){
		
	}
	
	void waitWorking(String workerType);*/
}