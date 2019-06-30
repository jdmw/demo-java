package jd.demo.se.concurrent.basic;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import jd.util.lang.Console;

public class TestFuture {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		/*CcUt.future(()->{
		return 1/0 ;
	}).get();*/
	
	ExecutorService executor = Executors.newSingleThreadScheduledExecutor() ;
	FutureTask<Integer> future  = new FutureTask<>(()->1);
	//future.cancel(true);
	Console.ln(future.get());
	}

}
