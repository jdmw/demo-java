package jd.designpattern.concurrent.future;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import jd.util.lang.Console;

public class FutureExecutor {

	public static <V> FutureTask<V> submit(Callable<V> callable){
		return new FutureTask<V>(callable).execute();
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<String> task = FutureExecutor.submit(()->{
			Console.tpln("[%T][%t] run task" );
			//CcUt.sleep(1000);
			return UUID.randomUUID().toString();
		});
		Console.tpln("[%T][%t] get result : " + task.get());
		
		FutureExecutor.submit(()->1/0).get();
	}

}
