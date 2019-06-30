package jd.demo.ds.tech.rpc.server.rpcontext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleRpcContext implements IRpcContext {

	private Map<String,Object> map = new ConcurrentHashMap<>();
	
	//@Override
	public <I, T extends I> void register(Class<I> interfaceClass, T instance) {
		map.put( interfaceClass.getName(),  instance);
	}

	@Override
	public <I> I get(String interfaceClass) {
		return (I) map.get(interfaceClass);
	}

}
