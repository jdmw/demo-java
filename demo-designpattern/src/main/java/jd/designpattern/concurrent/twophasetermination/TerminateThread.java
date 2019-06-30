package jd.designpattern.concurrent.twophasetermination;

public abstract class TerminateThread extends Thread{

	private volatile boolean shutdownRequested ;


	public void shutdown() {
		shutdownRequested = true ;
		// interrupt if it's sleeping
		this.interrupt();
	}
	
	public final void run() {
		try {
			while(!shutdownRequested) {
				doWork();
			}
		}catch(InterruptedException e) {
			
		}finally {
			doShutdown();
		}
	}

	abstract void doWork() throws InterruptedException;
	
	abstract void doShutdown() ;
}
