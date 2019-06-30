package jd.demo.ds.tech.rpc;

import java.io.IOException;

import jd.demo.ds.tech.rpc.communication.ISerial;
import jd.demo.ds.tech.rpc.communication.JdkSerial;
import jd.demo.ds.tech.rpc.server.RpcServer;
import jd.demo.ds.tech.rpc.server.rpcontext.SimpleRpcContext;
import jd.demo.example.math.Calculator;
import jd.demo.example.math.ICalculator;
import jd.demo.example.person.job.Actor;
import jd.demo.example.person.job.IActor;
import jd.util.lang.concurrent.CcUt;

public class RpcServerMain {

	public static int PORT = 8083 ;
	public static ISerial serial = new JdkSerial();
	
	public static void main(String[] args) {
		
		CcUt.start(()->{
			SimpleRpcContext context = new SimpleRpcContext();
			/*
			 * 
			 * context.register(IWalker.class, new DisableWalker());
			 */
			context.register(IActor.class, new Actor("Keah"));
			context.register(ICalculator.class,new Calculator());
			
			try {
				new RpcServer(PORT,serial,context).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, false);

	}

	
    public static interface IWalker {
    	public void walk();
    }
    public static class DisableWalker implements IWalker{
    	public void walk() {
    		throw new RuntimeException("It's disable");
    	}
    }
    
}
