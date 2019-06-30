package jd.designpattern.concurrent.guardedsuspension;

import java.util.UUID;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class ClientServerMain {

	public static void main(String[] args) {
		RequestQueue rq = new RequestQueue();
		Runnable clientThread = ()->{
			Request req = new Request(UUID.randomUUID().toString());
			Console.tpln("[%t] -> " + req );
			rq.putRequest(req);
			//CcUt.sleep(1000);
		};
		Runnable serverThread = ()->{
			Console.tpln("[%t] <- " + rq.getRequest()  ) ;
		} ;
		
		CcUt.startInLoop(true,1000,2,clientThread,clientThread,clientThread,clientThread);
		CcUt.startInLoop(true,500,4,serverThread,serverThread);
	}

}
