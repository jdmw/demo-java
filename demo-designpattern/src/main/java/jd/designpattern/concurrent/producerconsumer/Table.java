package jd.designpattern.concurrent.producerconsumer;

import jd.util.ArrUt;
import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

/**
 * elements stroage as a bridge between producer and customer 
 * it cannot be used in single-thread application because when storage space
 * is empty or full ,current executing thread would wait and no threads notify it
 * @author jdmw
 *
 * @param <T>
 */
public class Table<T> {
	private static final int DEFAULT_LEGNTH = 10;
	private final int bufLength ;
	private final Object[] buffer ;
	
	private int count ; // count of elements in the buffer array
	private int head ;// next position to put element at
	private int tail ;// next position to get element at
	
	public Table(){
		this(DEFAULT_LEGNTH);
	}
	
	public Table(int length){
		this.bufLength = length;
		this.buffer = new Object[length];
	}
	
	/**
	 * producer thread puts a element at the tail,wait if storage space is full 
	 * @param obj
	 */
	public synchronized void put(T obj) {
		// if buff is full,wait
		while(count>=bufLength) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		buffer[tail++] = obj ;
		if(tail >= bufLength) {
			tail = 0 ;
		}
		count ++ ;
		notifyAll();
	}
	
	/**
	 * consumer thread takes a elements storage space,wait if storage space is is empty
	 * @return
	 */
	public synchronized T take() {
		// if buff is empty,wait
		while (count <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Object target = buffer[head++];
		if(head >= bufLength) {
			head = 0 ;
		}
		count--;
		notifyAll();
		return (T) target ;
	}

	public static void main(String[] args) {
		Table<Integer> table = new Table<>();
		int num = 20 ;
		int[] arr = new int[num] ;
		ArrUt.fill(arr, i-> i);
		CcUt.join(CcUt.startInLoop(true, 100, 20, i->{
			table.put(arr[i]);
			Console.tpln("[%T][%t] put %d",arr[i]);
		},i->{
			Console.tpln("[%T][%t] will take");
			Integer e = table.take();
			Console.tpln("[%T][%t] take %d",e);
			if(e!=null) {
				arr[e] = -1 ;
			}else {
				Console.error("get error :"+e);
			}
		}));
		
		Console.ln(arr);
	}

}
