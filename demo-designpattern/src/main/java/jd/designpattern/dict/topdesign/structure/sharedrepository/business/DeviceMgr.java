package jd.designpattern.dict.topdesign.structure.sharedrepository.business;

import jd.designpattern.dict.topdesign.structure.sharedrepository.domainobj.IpDeviceDO;
import jd.designpattern.dict.topdesign.structure.sharedrepository.domainobj.RouterMO;

public class DeviceMgr {

	private RouterMO routerMO ;

	public DeviceMgr(RouterMO routerMO) {
		this.routerMO = routerMO;
	}

	
	/**
	 * connect to router with DHCP
	 * @param mac
	 * @return
	 */
	public long addDevice(IpDeviceDO device){
		if(device.isStaticIp()){
			return routerMO.addDevice(device.getMac(),device.getIp());
		}else{
			return routerMO.addDevice(device.getMac());
		}
	}

	/**
	 * log off
	 * @param device
	 * @return
	 */
	public boolean removeDevice(IpDeviceDO device){
		return routerMO.removeDevice(device.getMac()) == device.getIp() ;
	}
}
