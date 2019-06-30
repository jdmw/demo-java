package jd.demo.ds.tech.rpc.config;

import java.net.InetAddress;
import java.util.List;

public class RegistryInfo {
	
	
	private String appName ;
	private InetAddress address;
	private int port ;
	private List<String> interfaceClasses ;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public InetAddress getAddress() {
		return address;
	}
	public void setAddress(InetAddress address) {
		this.address = address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public List<String> getInterfaceClasses() {
		return interfaceClasses;
	}
	public void setInterfaceClasses(List<String> interfaceClasses) {
		this.interfaceClasses = interfaceClasses;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + port;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistryInfo other = (RegistryInfo) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
	
	
}
