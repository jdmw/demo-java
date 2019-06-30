package jd.demo.se.concurrent.model.threadandlock.philosopher;

import java.util.Random;

import jd.util.lang.Console;

public class Philosopher implements Runnable{

	static final int N = 5 ;
	static final boolean[] states = new boolean[N];
	static class Chopstick{
		private long id ;
	}
	
	private final Chopstick left, right ;
	private final Random random = new Random();
	
	Philosopher(Chopstick left,Chopstick right){
		this.left = left ;
		this.right = right ;
	}
	
	public void run() {
		long thinkTime = random.nextInt(1000) ;
		long eatTime = 3000 ;//random.nextInt(1000) ;
		try {
			while(true) {
				log(false);
				Thread.sleep(thinkTime);
				synchronized(left) {
					synchronized(right) {
						log(true);
						Thread.sleep(eatTime);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public synchronized void log(boolean eat) {
		int index = Integer.valueOf(Thread.currentThread().getName().split(" ")[1]) - 1 ;
		states[index] = eat ;
		String str = "[%T] %t is "+(eat?"eating  ":"thinking")+"\t" ;
		for(int i=0;i<N;i++) {
			str += states[i]?"+ " : "- " ;
		}
		Console.tp(str+"\n");
	}
	
	public static void main(String[] args) {
		Chopstick left = new Chopstick();
		Chopstick right = new Chopstick();
		left.id = 1 ;
		right.id = 2 ;
		Philosopher Philosopher = new Philosopher(left,right);
		for(int i=1;i<=N;i++) {
			new Thread(Philosopher,"Philosopher "+i).start();
		}
	}

}
