package jd.designpattern.concurrent.balking;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class BalkingJob {

	int count ;
	public synchronized int countUp() {
		return ++count;
	}
	private boolean jobInProgress  ;
	
	public boolean doJob() {
		synchronized(this) {
			if(jobInProgress ) { // a job is in progress
				Console.tpln("[%t] balking ");
				return false;		
			}
			jobInProgress  = false ;
		}
		doSomething();
		return true ;
	}
	
	private void doSomething() {
		CcUt.sleep((long)Math.random()*1000);
		Console.tpln("[%t] %d do something",countUp());
	}
	
	private synchronized void jobComplete() {
		jobInProgress  = true ;
		Console.tpln("[%t] a job is completed ");
	}
	
	
	public static void main(String[] args) {
		BalkingJob job = new BalkingJob();
		CcUt.prepare(()->{
			if(job.doJob()) ;//job.jobComplete();
		}).name("T%04d").daemon(false).times(3000).start().sleep(5000).join();
		/*CcUt.start(()->{
			
			if(job.doJob()) job.jobComplete();
		},3);*/
		Console.ln(job.count);
	}

}
