package jd.demo.ds.tech.rpc.server.rpcontext;

public interface IRpcContext {
	
	//public <I,T extends I> void register(Class<I> interfaceClass,T instance);
	
	
	public <I> I get(String interfaceClass);

}
