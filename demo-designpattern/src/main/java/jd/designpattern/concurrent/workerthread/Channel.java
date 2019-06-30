package jd.designpattern.concurrent.workerthread;

import jd.designpattern.concurrent.producerconsumer.Table;
import jd.util.ArrUt;
import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class Channel<T> {

	final Table<Request<T>> buf = new Table<>();
	final Thread[] workers ;
	
	public Channel(int num) {
		workers = new Thread[num];
		Runnable r = ()->{
			while(true) {
				try {
					buf.take().execute();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		for(int i=0;i<num;i++) {
			workers[i] = new Thread(r,"Worker-"+i);
		}
	}
	
	
	public void startConsumers() {
		for(Thread worker: workers) {
			worker.start();
		}
	}
	
	public void putRequest(Request<T> req) {
		buf.put(req);
	}
	
	public static void main(String[] args) {
		Channel<Integer> channel = new Channel<>(3);
		int num = 20 ;
		int[] arr = new int[num] ;
		ArrUt.fill(arr, i-> i);
		channel.startConsumers();
		CcUt.join(CcUt.startInLoop(true, 100, 10, i->{
			channel.putRequest(()->{
				Console.tpln("[%T][%t] set arr[%d] = -1 ",i);
				arr[i] = -1 ;
			});
			Console.tpln("[%T][%t] put %d",arr[i]);
		}, i->{
			channel.putRequest(()->{
				Console.tpln("[%T][%t] set arr[%d] = -1 ",i+10);
				arr[i+10] = -1 ;
			});
			Console.tpln("[%T][%t] put %d",arr[i+10]);
		}));
		Console.ln(arr);

	}

}
