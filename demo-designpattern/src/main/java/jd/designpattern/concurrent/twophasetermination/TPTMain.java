package jd.designpattern.concurrent.twophasetermination;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class TPTMain extends TerminateThread{

	public static void main(String[] args) {
		TerminateThread t = new TPTMain();
		t.start();
		CcUt.sleep(1000);
		t.shutdown();
	}

	volatile int i = 0 ;
	
	@Override
	void doWork() throws InterruptedException {
		Console.tpln("%t count up to %d", ++i);
	}

	@Override
	void doShutdown() {
		Console.tpln("%t shutdonw at count = %d", i);
		
	}

}
