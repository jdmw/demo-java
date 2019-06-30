package jd.demo.se.concurrent.server;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public abstract class TimeServer implements Runnable{

	public final static int DEFAULT_PORT = 81 ;
	
	public final static String QUERY_CMD = "QUERY TIME" ;
	
	public static String response(byte[] bs){
		try {
			String cmd = new String(bs,"UTF-8");
			if(cmd.equals(QUERY_CMD)){
				return "Current Time:" + new Date().toGMTString() ;
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		return "Bad Command" ;
	}
}
