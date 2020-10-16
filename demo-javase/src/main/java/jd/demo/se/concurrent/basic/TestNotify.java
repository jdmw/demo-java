package jd.demo.se.concurrent.basic;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class TestNotify {

	public synchronized void testWait() {
		Console.tpln("[%T - %t] is going to wait");
		try {
			wait();
		} catch (InterruptedException e) {
			Console.tpln("[%T - %t] is Interrupted ");
		}
		Console.tpln("[%T - %t] resume from waiting");
	}
	
	public synchronized void testnofity()  {
		Console.tpln("[%T - %t] hold lock");
		if(Math.random()>0.5) {
			Console.tpln("[%T - %t] is going to sleep");
			CcUt.sleep(100);
			Console.tpln("[%T - %t] awake from sleeping");
		}else {
			testWait();
		}
		
		notify();
		Console.tpln("[%T - %t] release lock");
	}
	
	public static void main(String[] args) {
		TestNotify tn = new TestNotify();
		CcUt.startInPriority(()->{
			tn.testWait();
		},Thread.MIN_PRIORITY).setName("wait-thread");
		
		CcUt.join(CcUt.startInPriority(()->tn.testnofity(),
				Thread.MAX_PRIORITY,2,"notify-thread-%d"));
		CcUt.start(()->{
			try {
				tn.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Console.tpln("[%T - %t] resume from waiting");
		});
	}

}
