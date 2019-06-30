package jd.demo.se.concurrent.model.threadandlock;

import jd.util.lang.Console;

public class DemoCreateThread {

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread() {
			@Override
			public void run() {
				Console.tp("[%t]running new thread\n");
				/*try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		};
		//t.setDaemon(true);
		t.start();
		Thread.yield();
		//Thread.sleep(30);
		Console.tp("[%t]running main thread\n");
		t.join();
	}

}
