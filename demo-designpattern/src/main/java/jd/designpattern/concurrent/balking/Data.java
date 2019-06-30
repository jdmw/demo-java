package jd.designpattern.concurrent.balking;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class Data {

	private volatile boolean changed = false; 
	private volatile String content ;
	public synchronized void save() {
		if(!changed) {
			Console.tpln("%t balking");
			return  ;
		}
		doSave();
		changed = false ;
	}
	public synchronized void change(String content) {
		this.content = content ;
		changed = true ;
		Console.tpln("%t change: %s",content);
	}
	
	private void doSave() {
		Console.tpln("%t save : %s" ,content);
	}
	
	public static void main(String[] args) {
		Data data =  new Data();
		
		CcUt.prepare(()->{
			data.change(Thread.currentThread().getName().split("-")[1]);
		},()->{
			data.save();
		}).name("T%d-%d").times(10).start();

		
		/**
		CcUt.startInLoop(true,100,30,()->{
			data.change(UUID.randomUUID().toString());
		},()->{
			data.save();
		});
		 */
	}

}
