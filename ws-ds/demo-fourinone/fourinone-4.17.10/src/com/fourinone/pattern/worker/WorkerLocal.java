package com.fourinone.pattern.worker;

import com.fourinone.pattern.warehouse.WareHouse;

public interface WorkerLocal extends WorkerProxy
{
	public WareHouse doTask(WareHouse inhouse);
	public WareHouse doTask(WareHouse inhouse, long timeoutseconds);
	public void interrupt();
	public String getHost();
	public int getPort();
}