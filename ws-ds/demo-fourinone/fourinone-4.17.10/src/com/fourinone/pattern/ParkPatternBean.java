package com.fourinone.pattern;

import com.fourinone.pattern.warehouse.ObjectBean;
import com.fourinone.RecallException;
import com.fourinone.pattern.warehouse.WareHouse;

public class ParkPatternBean
{
	String domain,node;
	WareHouse inhouse,outhouse;
	ObjectBean thisversion;
	RecallException rx;
	public ParkPatternBean(String domain, String node, WareHouse inhouse, WareHouse outhouse, RecallException rx)
	{
		this.domain = domain;
		this.node = node;
		this.inhouse = inhouse;
		this.outhouse = outhouse;
		this.rx = rx;
	}
}