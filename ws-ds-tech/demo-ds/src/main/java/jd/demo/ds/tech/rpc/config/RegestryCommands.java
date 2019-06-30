package jd.demo.ds.tech.rpc.config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class RegestryCommands {

	public static enum CommandType {registry,checkAlive,list} ;
	
	public static boolean sendRegistry(ObjectInputStream is, ObjectOutputStream os,RegistryInfo info) throws IOException {
		os.writeByte(CommandType.registry.ordinal());
		// a provider send application name , netAddresss, port,interfaceClasses
		os.flush();
		
		// a provider receive callback
		return is.readBoolean();
	}
	

	public static RegistryInfo receiveRegistry(ObjectInputStream is, ObjectOutputStream os) throws IOException  {
		// the registry read application name , netAddresss, port,interfaceClasses
		RegistryInfo info = new RegistryInfo();
		os.flush();
		
		// the registry send callback
		os.writeBoolean(true);
		return info ;
	}
	
	
	public static void sendHeartBeat(ObjectInputStream is, ObjectOutputStream os) throws IOException  {
		os.writeByte(CommandType.checkAlive.ordinal());
		// do nothing else
		os.flush();
	}
	
	public static void echoAlive(ObjectInputStream is, ObjectOutputStream os) throws IOException  {
		// do nothing
		os.writeBoolean(true);
		os.flush();
	}
	
	public static void commandHandle(ObjectInputStream is, ObjectOutputStream os) throws IOException {
		int command = is.readInt();
		/*
		 * switch(CommandType.valueOf(command)) { case CommandType.registry : {
		 * 
		 * } }
		 */
		
	}
}
