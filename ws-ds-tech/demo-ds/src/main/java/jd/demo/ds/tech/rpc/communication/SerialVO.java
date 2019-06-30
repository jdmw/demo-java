package jd.demo.ds.tech.rpc.communication;

public class SerialVO {

	private String interfaceClass ;
	private String methodName ;
	private Class<?>[] parameterTypes;
	private Object[] args ;
	
	
	public String getInterfaceClass() {
		return interfaceClass;
	}
	public void setInterfaceClass(String interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	
}
