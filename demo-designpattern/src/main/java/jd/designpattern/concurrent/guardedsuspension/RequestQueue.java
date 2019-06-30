package jd.designpattern.concurrent.guardedsuspension;

import java.util.LinkedList;
import java.util.Queue;

public class RequestQueue {

	private final Queue<Request> queue = new LinkedList<>();

	public synchronized void putRequest(Request req) {
		queue.offer(req);
		notifyAll();
	}
	
	public synchronized Request getRequest() {
		while(queue.peek() == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return queue.remove();
	}
}


