package jd.demo.ds.tech.rpc;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import jd.demo.ds.tech.rpc.client.proxy.RpcClient;
import jd.demo.ds.tech.rpc.communication.ISerial;
import jd.demo.example.math.ICalculator;
import jd.demo.example.person.job.IActor;

public class RpcClientMain {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		int port = RpcServerMain.PORT ;
		ISerial serial = RpcServerMain.serial;
		
		
		RpcClient client = new RpcClient(Inet4Address.getLocalHost(),port,serial);
		
		IActor actor = client.proxy(IActor.class);
		actor.act();
		
		
		ICalculator calc = (ICalculator) client.proxy(ICalculator.class);
		try {
			System.out.println("1+2="+calc.add(1, 2));
			System.out.println("10000*10000="+calc.multiply(10000,10000));
			System.out.print("1/0=");
			System.out.println(calc.divide(1, 0));
		}catch(ArithmeticException e) {
			System.err.println(e.getMessage());
		}
		
		
	}

}
