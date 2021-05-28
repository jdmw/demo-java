package com.fourinone.pattern.worker;

import com.fourinone.pattern.warehouse.WareHouse;

public interface Workman
{
	public boolean receive(WareHouse inhouse);
	public String getHost();
	public int getPort();
}