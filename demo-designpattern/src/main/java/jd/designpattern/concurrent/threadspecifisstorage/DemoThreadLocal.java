package jd.designpattern.concurrent.threadspecifisstorage;

import java.util.concurrent.atomic.AtomicInteger;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class DemoThreadLocal {

	// Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId =
        new ThreadLocal<Integer>() {
            @Override protected Integer initialValue() {
                return nextId.getAndIncrement();
        }
    };
    
    private static int getId() {
    	return threadId.get().intValue();
    }
    
    
	public static void main(String[] args) {
		CcUt.start(()->{
			Console.tpln("[%t] get id : %d",getId());
		}, 10);

	}

}
