package jd.designpattern.concurrent.guardedsuspension;

import java.util.UUID;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class ClientServerMain2 {

	public static void main(String[] args) {
		RequestQueue2 rq = new RequestQueue2();
		Runnable clientThread = ()->{
			Request req = new Request(UUID.randomUUID().toString());
			Console.tpln("[%t] -> " + req );
			rq.putRequest(req);
			//CcUt.sleep(1000);
		};
		Runnable serverThread = ()->{
			Console.tpln("[%t] <- " + rq.getRequest()  ) ;
		} ;
		
		CcUt.startInLoop(true,1000,4,clientThread,clientThread,clientThread,clientThread);
		CcUt.startInLoop(true,500,4,serverThread,serverThread);
	}

}
