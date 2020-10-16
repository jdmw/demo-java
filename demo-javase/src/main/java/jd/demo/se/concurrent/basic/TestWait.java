package jd.demo.se.concurrent.basic;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class TestWait {

	public static void main(String[] args) {
		 Object obj = new Object();
		 CcUt.start(()->{
			 while(true) {
				 CcUt.sleepMs(1);
				synchronized (obj) {
					Console.tpln("[%t] wait");
					try {
						obj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			 }
		 },2);
	}

}
	