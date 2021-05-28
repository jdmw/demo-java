package com.fourinone.pattern;

import com.fourinone.pattern.warehouse.WareHouse;

public abstract class SuperContractor extends Contractor {
	protected CtorLocal[] getWaitingCtors(String ctorType){
		return getLocals(ctorType, CtorLocal.class);
	}
	
	protected WareHouse[] giveTaskBatch(CtorLocal[] cls, WareHouse wh){
		return doTaskBatch(wh);
	}
}