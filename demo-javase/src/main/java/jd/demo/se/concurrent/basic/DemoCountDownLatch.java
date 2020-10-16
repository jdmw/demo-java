package jd.demo.se.concurrent.basic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

public class DemoCountDownLatch implements Runnable{

	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;
	DemoCountDownLatch(CountDownLatch startSignal, CountDownLatch doneSignal) {
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
	}
	public void run() {
		try {
			startSignal.await();
			doWork();
			doneSignal.countDown();
		} catch (InterruptedException ex) {
		} // return;
	}
	
	void doWork() {
		System.out.println(Thread.currentThread().getName() +": do some work");
	}
	
	public static void main(String[] args) throws InterruptedException {
		final int N  = 3 ;
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(N);
	
		for (int i = 0; i < N; ++i) // create and start threads
		new Thread(new DemoCountDownLatch(startSignal, doneSignal)).start();
	
		System.out.println("Prepare to work"); // don't let run yet
		startSignal.countDown();      // let all threads proceed
		System.out.println("Starting work"); 
		doneSignal.await();           // wait for all to finish
		System.out.println("Finished to work");
		
		
	}

}
