package jd.demo.se.concurrent.model.threadandlock.philosopher.solution;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import jd.util.lang.Console;

public class Philosopher extends Thread{

	static final int N = 5 ;
	static final boolean[] states = new boolean[N];
	
	private boolean eating ;
	private final ReentrantLock table ;
	private final Condition condition ;
	private Philosopher left, right ;
	private final Random random;
	
	Philosopher(ReentrantLock table){
		this.table = table ;
		this.condition = table.newCondition();
		this.random = new Random();
	}
	
	public void setLeft(Philosopher left) {
		this.left = left;
		left.right = this ;
	}

	public void setRight(Philosopher right) {
		this.right = right;
		right.left = this ;
	}
	
	
	private void think() throws InterruptedException {
		table.lock();
		try {
			eating = false ;
			log(eating);
			left.condition.signal();
			right.condition.signal();
		} finally {
			table.unlock();
		}
		Thread.sleep(1000);
		
	}
	
	

	private void eat() throws InterruptedException {
		table.lock();
		try {
			while(left.eating || right.eating) {
				condition.await();
			}
			eating = true ;
			log(eating);
		} finally {
			table.unlock();
		}
		Thread.sleep(1000);
	}
	
	
	public void run() {
		try {
			while(true) {
				think();
				eat();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public synchronized void log(boolean eat) {
		int index = Integer.valueOf(Thread.currentThread().getName().split("-")[1]) - 1 ;
		states[index] = eat ;
		String str = "[%T] %t is "+(eat?"eating  ":"thinking")+"\t" ;
		for(int i=0;i<N;i++) {
			str += states[i]?"+ " : "- " ;
		}
		Console.tp(str+"\n");
	}
	
	public static void main(String[] args) {
		ReentrantLock table = new ReentrantLock();
		Philosopher[] phs = new Philosopher[N];
		int i = 0;
		do {
			phs[i] = new Philosopher(table);
			phs[i].setName("ph-" + (i+1));
			if(i>0) {
				phs[i].setLeft(phs[i-1]);
			}
		}while(++i<N);

		phs[0].setLeft(phs[N-1]);
		
		while(i-->0) {
			phs[i].start();
		}
	}

}
