package jd.designpattern.dict.topdesign.structure.sharedrepository.domainobj;


public class RouterDO extends IpDeviceDO {

	private String ssid ;
	private String password ;
	
	public RouterDO(long ip) {
		super(ip);
	}
	
	public long getNetNo(){
		return super.getIp() / 256 ;
	}

	public String getSsid() {
		return ssid;
	}

	public String getPassword() {
		return password;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
