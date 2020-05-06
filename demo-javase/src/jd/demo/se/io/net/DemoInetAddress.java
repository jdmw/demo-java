package jd.demo.se.io.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import jd.util.io.IOUt;
import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class DemoInetAddress{
   public static void main(String args[]) throws IOException {
      InetAddress remAddr = null ;
      InetAddress locAddr = null ;

       String host = "www.baidu.com" ;
	   remAddr = InetAddress.getByName(host) ;
	   System.out.println(host + ":" + remAddr.getHostAddress()) ;

	   locAddr = InetAddress.getLocalHost() ;
	   byte[] ips = locAddr.getAddress() ;
	   System.out.println("locAddr:" + locAddr.getHostAddress()) ;
	   System.out.println("isReachable: " + locAddr.isReachable(500)) ;
      
      int port = 8881 ;
		ServerSocket ser = null;
		try {
			ser = new ServerSocket(port);
			CcUt.start(()->{
				try {
					Socket s = new Socket("localhost",port);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			Socket client = ser.accept() ;
			
			InetAddress addr = InetAddress.getLocalHost(); // InetAddress.getByAddress(new byte[] {(byte) 200,(byte) 181,57,(byte) 216});
			Console.ln(addr.getHostAddress(),"\n",addr.getHostName());
			System.out.println(addr.getLocalHost());
			Console.ln(addr.getLoopbackAddress(),"\n",addr.getCanonicalHostName());
			
			System.out.println();
			SocketAddress saddr = ser.getLocalSocketAddress();
			Console.ln(saddr);
			
			
			Console.ln("getInetAddress: ",client.getInetAddress());
			Console.ln("getLocalAddress:",client.getLocalAddress());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUt.close(ser);
		}
		
   }
};
