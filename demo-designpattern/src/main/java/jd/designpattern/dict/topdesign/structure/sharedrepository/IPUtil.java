package jd.designpattern.dict.topdesign.structure.sharedrepository;

public class IPUtil {
	private IPUtil(){}
	
	public static long calcIP(int ip1,int ip2,int ip3,int ip4){
		long ip = ip1 ;
		ip = ip * 256 + ip2 ;
		ip = ip * 256 + ip3 ;
		ip = ip * 256 + ip4 ;
		return ip ;
	}
	
	public static long calcIP(long netNo,int ip4){
		return netNo * 256 + ip4 ;
	}
}
