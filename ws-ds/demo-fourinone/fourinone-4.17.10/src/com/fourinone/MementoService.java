package com.fourinone;

import com.fourinone.util.LogUtil;

import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;

public class MementoService{
	protected String getClientHost(){
		String clienthost=null;
		try{
			clienthost = RemoteServer.getClientHost();
		}catch(ServerNotActiveException ex){
			LogUtil.fine("[MementoService]", "[getClientHost]", ex);
		}
		//System.out.println("clienthost:"+clienthost);
		return clienthost;
	}
}