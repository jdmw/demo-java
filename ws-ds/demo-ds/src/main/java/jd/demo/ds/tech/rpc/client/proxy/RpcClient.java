package jd.demo.ds.tech.rpc.client.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.Socket;

import jd.demo.ds.tech.rpc.communication.ISerial;
import jd.demo.ds.tech.rpc.communication.ResultSerialVO;
import jd.util.lang.reflect.ReflectUt;

public class RpcClient {

    final ISerial serial ;
    final InetAddress addr;
    final int port ;
    
    public RpcClient(InetAddress addr,int port, ISerial serial) {
        this.serial = serial ;
        this.addr = addr ;
        this.port = port ;
    }
    
    public <I> I proxy(Class<I> interfaceClass) {
    	return  (I) Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class[] {interfaceClass}, 
    			new RpcInvocationHandler(interfaceClass));
    }

    private class RpcInvocationHandler implements InvocationHandler{

    	private Class<?> interfaceClass ;
    	
		public RpcInvocationHandler(Class<?> interfaceClass) {
			this.interfaceClass = interfaceClass;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	        try(Socket socket = new Socket(addr,port)) {
				serial.write(socket.getOutputStream(), interfaceClass, method, args);
				ResultSerialVO vo = serial.readResult(socket.getInputStream());
				vo.setHasResult(!void.class.equals(method.getReturnType()));
				if(vo.isHasError()) {
					throw vo.getThrowable();
				}
				if(vo.isHasResult()) {
					return ReflectUt.toPrimaryType(vo.getResult(),method.getReturnType());
				}
				return null;
	        }
		}
		
    }
    


	
	
	

}
