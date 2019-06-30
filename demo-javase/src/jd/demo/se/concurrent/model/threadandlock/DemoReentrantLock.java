package jd.demo.se.concurrent.model.threadandlock;

import java.util.concurrent.locks.ReentrantLock;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class DemoReentrantLock {

	
	public static void main(String[] args) throws InterruptedException {
		ReentrantLock lock1 = new ReentrantLock();
		ReentrantLock lock2 = new ReentrantLock();
		Thread[] ts = CcUt.startAtSameTime(()->{
			try {
				lock1.lockInterruptibly();
				Thread.sleep(1000);
				lock2.lockInterruptibly();
				Console.tp("[%t] do something");
				lock2.unlock();
				lock1.unlock();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		},()->{
			try {
				lock2.lockInterruptibly();
					Thread.sleep(1000);
					lock1.lockInterruptibly();
					Console.tp("[%t] do something");
					lock1.unlock();
					lock2.unlock();
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
