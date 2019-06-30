package jd.demo.se.concurrent.basic;

import jd.util.lang.concurrent.CcUt;

public class TestSynchronizedHeritate implements Runnable{

	/**
	 * 测试：
	 *  1 synchronized关键字不影响方法继承
	 * 	2 run()方法不能同步synchronized，否则其中一条线程永久占用，其他线程无法激活 
	 */
	@Override
	public synchronized void run() {
		while(true) {
			System.out.println(Thread.currentThread().getName() + " rinning");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		CcUt.start(new TestSynchronizedHeritate(),5); 
		
	}
	
	
	static interface TestSynchronizedInterface{
		public void method1();
		// 抽象方法不可设置synchronized关键字
		// public synchronized abstract void method2();
	}
}

