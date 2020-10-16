package jd.demo.se.concurrent.model.threadandlock;

import jd.util.lang.concurrent.CcUt;

public class DeadLock {

	
	
	
	public static void main(String[] args) throws InterruptedException {
		final Object o1 = new Object();
		final Object o2 = new Object();
		Thread[] ts = CcUt.startAtSameTime(()->{
			try {
				synchronized(o1) {
					Thread.sleep(1000);
					synchronized(o2) {
						
					}
				}
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		},()->{
			try {
				synchronized(o2) {
					Thread.sleep(1000);
					synchronized(o1) {
						
					}
				}
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		

		Thread.sleep(2000);
		ts[0].interrupt();
		ts[1].interrupt();
		ts[0].join();
		ts[1].join();

	}

}
