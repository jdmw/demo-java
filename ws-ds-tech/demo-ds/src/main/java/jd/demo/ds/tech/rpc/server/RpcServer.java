package jd.demo.ds.tech.rpc.server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jd.demo.ds.tech.rpc.communication.ISerial;
import jd.demo.ds.tech.rpc.communication.SerialVO;
import jd.demo.ds.tech.rpc.server.rpcontext.IRpcContext;
import jd.util.lang.Console;


public class RpcServer {
	private ServerSocket server ;
    final IRpcContext services;
    final ISerial serial ;
    final int port ;
    
    public RpcServer(int port, ISerial serial,IRpcContext services) throws IOException {
        this.services = services;
        this.serial = serial ;
        this.port = port ;
        //this.server = new ServerSocket(port);
    }

    public void start() throws IOException {
    	this.server = new ServerSocket(port);
    	System.out.println("server is running at port " + port);
    	ExecutorService pool = Executors.newWorkStealingPool();
    	Socket socket = null ;
    	while((socket = server.accept()) != null) {
    		Socket client = socket  ;
    		pool.execute(()->{
    			try {
    				InputStream is = client.getInputStream();
    				OutputStream os = client.getOutputStream();
    				SerialVO vo = serial.read(is);
	    	        Object service = services.get(vo.getInterfaceClass());
	    	        Class interfaceName = Class.forName(vo.getInterfaceClass(),true,service.getClass().getClassLoader());
	    	        Method method = service.getClass().getMethod(vo.getMethodName(),vo.getParameterTypes());
	    	        
	    	        Object result =  null ;
	    	        Throwable error = null ;
	    	        try{
	    	        	result = method.invoke(service,vo.getArgs());
	    	        }catch(InvocationTargetException ite) {
	    	        	error = ite.getTargetException();
	    	        }catch(Throwable e) {
	    	        	error = e ;
	    	        }finally {
	    	        	serial.writeResult(os, method.getReturnType() != null, result, error);
	    	        }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                	try {
						client.close();
					} catch (IOException e) {
						try {
							client.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
                }
    		});
    	}
    }



}
