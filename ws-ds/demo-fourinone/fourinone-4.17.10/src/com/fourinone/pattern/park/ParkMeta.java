package com.fourinone.pattern.park;

import com.fourinone.ConfigContext;
import com.fourinone.MulBean;

import java.util.HashMap;
import java.util.Map;

public enum  ParkMeta
{
	PARK_META,VERSION,AUTH,PROP,
	CREATE_BY,CREATE_IP,CREATE_TIME,
	UPDATE_BY,UPDATE_IP,UPDATE_TIME,
	HEARTBEAT
	;
	
	static Map<String,String> CONFIG = new HashMap<>();
	static{
		MulBean mb = ConfigContext.getMulBean();
		for(ParkMeta m : ParkMeta.values()){
			CONFIG.put(m.name(),mb.getString("PARK_META_"+m.name()));
		}
	}

	public String get(String domainNodeKey){
		return domainNodeKey + get(this);
	}
	private static String get(ParkMeta meta){
		return CONFIG.get(meta.name());
	}
	static String getMeta(){
		return get(PARK_META);
	}
	static String getVersion(){
		return get(VERSION);
	}
	static String getVersion(String domainNodeKey){
		return domainNodeKey+ get(VERSION);
	}
	static String getCreateBy(String domainNodeKey){
		return domainNodeKey+get(CREATE_BY);
	}
	static String getCreateIP(String domainNodeKey){
		return domainNodeKey+get(CREATE_IP);
	}
	static String getCreateTime(){
		return get(CREATE_TIME);
	}
	static String getCreateTime(String domainNodeKey){
		return domainNodeKey+get(CREATE_TIME);
	}
	static String getAuth(String domainNodeKey){
		return domainNodeKey+get(AUTH);
	}
	static String getProp(){
		return get(PROP);
	}
	static String getProp(String domainNodeKey){
		return domainNodeKey+get(PROP);
	}
	static String getUpdateBy(String domainNodeKey){
		return domainNodeKey+get(UPDATE_BY);
	}
	static String getUpdateIP(String domainNodeKey){
		return domainNodeKey+get(UPDATE_IP);
	}
	static String getUpdateTime(String domainNodeKey){
		return domainNodeKey+get(UPDATE_TIME);
	}
	static String getHeartBeat(){
		return get(HEARTBEAT);
	}
	
	public static void main(String[] args)
	{
		System.out.println("ParkMeta.getVersion():"+getVersion());
	}
}