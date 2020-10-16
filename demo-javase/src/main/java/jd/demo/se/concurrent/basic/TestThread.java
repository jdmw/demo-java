package jd.demo.se.concurrent.basic;

import jd.util.lang.concurrent.CcUt;

public class TestThread {

	static Runnable r1 =  new Runnable(){
		@Override
		public synchronized void run() {
			while(true) {
				System.out.println(Thread.currentThread().getName() + " rinning");
			}
		}
	};
	
	static Runnable r2 =  new Runnable(){
		int i ;
		@Override
		public synchronized void run() {
			System.out.println("i = " + (i++));
		}
	};

	public static void main(String[] args) throws InterruptedException {
		//CcUt.start(r1,2);
		CcUt.start(r2,200);
	}
	
}

