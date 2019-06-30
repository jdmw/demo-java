package jd.designpattern.dict.topdesign.structure.sharedrepository.domainobj;

import java.util.HashMap;
import java.util.Map;

import jd.designpattern.dict.topdesign.structure.sharedrepository.IPUtil;
import jd.util.lang.math.RandomUt;

public class RouterMO {
	
	private RouterDO router = new RouterDO(IPUtil.calcIP(192,168,1,1));
	private Map<Long,Long> deviceTimeMap = new HashMap<>();// MAC address - connect time
	private Map<Long,Long> deviceIpMap = new HashMap<>();  // MAC address - IP address
	
	
	/**
	 * connect to router with DHCP
	 * @param mac
	 * @return
	 */
	public long addDevice(long devicemac){
		long ip = assignIp() ;
		if(ip != -1){
			long time = System.currentTimeMillis();
			deviceTimeMap.put(devicemac,time );
			deviceIpMap.put(devicemac, ip);
		}
		return ip;
	}
	
	/**
	 * connect to router with static IP
	 * @param devicemac
	 * @param staticIp
	 * @return
	 */
	public long addDevice(long devicemac,long staticIp){
		if(!deviceIpMap.containsKey(staticIp) && (long)(staticIp/256) == router.getNetNo() ){
			long time = System.currentTimeMillis();
			deviceTimeMap.put(devicemac,time );
			deviceIpMap.put(devicemac, staticIp);
			return staticIp;
		}else{
			return -1 ;
		}
	}
	
	/**
	 * 
	 * @param devicemac
	 * @return device's ip
	 */
	public Long removeDevice(long devicemac){
		deviceTimeMap.remove(devicemac );
		return deviceIpMap.remove(devicemac);
	}
	
	/** 
	 * assign a ip for 
	 * @return
	 */
	private long assignIp(){
		long ip = router.getIp();
		long ip4 = ( ip  + RandomUt.random(1, 255) ) % 256;
		return (long)(ip/256) * 256 + ip4 ;
	}
	
	
}
