package jd.designpattern.dict.topdesign.structure.sharedrepository.domainobj;

public class IpDeviceDO {
	private long mac = random(6);  // 6 bytes
	private long ip ; // 4 bytes
	private boolean active ; // connectec to other device
	private boolean poweron = true ;
	private boolean isStaticIp ;
	private String name ;
	private String type ;
	
	public IpDeviceDO(long ip) {
		this.ip = ip;
	}
	public long getMac() {
		return mac;
	}
	public long getIp() {
		return ip;
	}
	public boolean isActive() {
		return active;
	}
	public boolean isPoweron() {
		return poweron;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public void setMac(long mac) {
		this.mac = mac;
	}
	public void setIp(long ip) {
		this.ip = ip;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setPoweron(boolean poweron) {
		this.poweron = poweron;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public static long random(int types){
		long l = 0;
		while(types-->0){
			l += (Math.random() * (2^8));
		}
		return l ;
	}
	public boolean isStaticIp() {
		return isStaticIp;
	}
	public void setStaticIp(boolean isStaticIp) {
		this.isStaticIp = isStaticIp;
	}
}
