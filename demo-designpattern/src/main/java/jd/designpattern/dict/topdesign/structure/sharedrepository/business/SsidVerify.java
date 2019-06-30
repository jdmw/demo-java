package jd.designpattern.dict.topdesign.structure.sharedrepository.business;

import java.util.ArrayList;
import java.util.List;

import jd.designpattern.dict.topdesign.structure.sharedrepository.domainobj.RouterDO;

public class SsidVerify {

	private final List<RouterDO> routers = new ArrayList<>();
	
	public boolean register(RouterDO rd){
		String ssid = rd.getSsid();
		if(ssid != null && !ssid.isEmpty() && null == findRouterDO(ssid)){
			routers.add(rd);
			return true ;
		}else {
			return false;
		}
	}
	
	public void deregister(RouterDO rd){
		routers.remove(rd);
	}
	
	/**
	 * verify password
	 * @param ssid
	 * @param password
	 * @return
	 */
	public boolean verify(String ssid,String password){
		RouterDO rd = findRouterDO( ssid);
		return (rd != null && rd.getPassword().equals(password));
	}
	
	private RouterDO findRouterDO(String ssid){
		for(RouterDO rd : routers){
			if(rd.getSsid().equals(ssid)){
				return rd ;
			}
		}
		return null;
	}
}
