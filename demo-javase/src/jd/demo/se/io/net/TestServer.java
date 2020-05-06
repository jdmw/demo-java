package jd.demo.se.io.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(82);
		while(true){
			Socket socket = server.accept();
			
			InputStream is = socket.getInputStream();
			
			StringBuffer sb = new StringBuffer();
			int i ;
			while((i=is.read()) != -1){
				sb.append((char)i);
			}
			System.out.println("msg from client:"+sb);
			socket.getOutputStream().write("response".getBytes());
			socket.close();
			server.close();
		}
	}
	public void client(){

		/*
		InetAddress addr = InetAddress.getLocalHost();
		Socket s = new Socket(addr, 82);
		OutputStream os = s.getOutputStream();
		os.write("SHUTDOWN".getBytes());
		os.flush();
		//os.close();
		InputStream is = s.getInputStream();
		byte[] bs = new byte[1024] ;
		is.read(bs);
		System.out.println(bs);
		s.close();
		*/
	}

}
