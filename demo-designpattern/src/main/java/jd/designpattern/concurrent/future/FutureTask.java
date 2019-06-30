package jd.designpattern.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import jd.util.lang.concurrent.CcUt;

public class FutureTask<V> extends Thread implements Runnable, Future<V>{

	private final Callable<V> callable ;
	private V result ;
	private ExecutionException err ;
	private volatile boolean started ;
	private volatile boolean cancelled ;
	private volatile boolean done ;
	
	public FutureTask(Callable<V> callable) {
		this.callable = callable ;
	}
	
	public FutureTask<V> execute() {
		//new Thread(this).start();
		this.start();
		return this ;
	}
	
	private synchronized void executeTask() {
		//Console.tpln("[%t] before executeTask");
		if(started || cancelled ) {
			return ;
		}
		started = true ;
		try {
			result = callable.call();
		} catch (Throwable e) {
			this.err = new ExecutionException(e);
		} finally {
			done = true ;
			notifyAll();
		}
	}
	
	private synchronized void waitForComputation() throws InterruptedException {
		while(!done) {
			//Console.tpln("[%t] before wait");
			wait();
			//Console.tpln("[%t] after wait");
		}
	}
	

	@Override
	public synchronized boolean cancel(boolean mayInterruptIfRunning) {
		if(!started ) {
			// it has not started
			cancelled = true ;
			return true ;
		}else if(done){
			// it completed normally.
			return false ;
		}else {
			// it's running
			if(mayInterruptIfRunning) {
				try{
					interrupt();
				}catch(Exception e) {
					e.printStackTrace();
				}
				return this.isInterrupted();
			}
		}
		return false;
	}


	@Override
	public synchronized V get() throws InterruptedException, ExecutionException {
		if(!cancelled ) {
			if(!done && super.getState() == State.NEW ) {
				this.start();
			}
			waitForComputation();
		}
		if(err != null) {
			throw err ;
		}
		return result;
	}

	@Override
	public synchronized V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if(!cancelled) {
			if(!started  && super.getState() == State.NEW) {
				this.start();
			}
			if(!done && timeout >0) {
				CcUt.sleep(timeout,unit);
				if(!done) {
					throw new TimeoutException("the wait timed out");
				}
			}
		}
		if(err != null) {
			throw err ;
		}
		return result;
	}

	@Override
	public void run() {
		//Console.tpln("[%t] thread starting");
		executeTask();
		//Console.tpln("[%t] thread finished");
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public boolean isDone() {
		return done;
	}

}
