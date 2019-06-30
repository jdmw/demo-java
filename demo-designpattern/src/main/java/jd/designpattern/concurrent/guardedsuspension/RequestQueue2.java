package jd.designpattern.concurrent.guardedsuspension;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestQueue2 {

	private final BlockingQueue<Request> queue = new LinkedBlockingQueue<>();

	public synchronized void putRequest(Request req) {
		queue.offer(req);
	}
	
	public synchronized Request getRequest() {
		Request req = null ;
		try {
			req = queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return req ;
	}
	
	
}


