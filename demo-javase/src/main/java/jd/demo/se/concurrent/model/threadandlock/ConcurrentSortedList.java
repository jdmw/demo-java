package jd.demo.se.concurrent.model.threadandlock;

import java.util.concurrent.locks.ReentrantLock;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;
import jd.util.lang.math.RandomUt;

/**
 * Hand-over-Hand lock
 * @author jdmw
 *
 * @param <T>
 */
public class ConcurrentSortedList<T extends Comparable> /*extends AbstractList<T> */{

	final static class Node<T> {
		T value ;
		Node<T> prev ;
		Node<T> next ;
		ReentrantLock lock = new ReentrantLock();
		Node(){
		}
		Node(T value,Node<T> prev,Node<T> next){
			this.value = value ;
			this.prev = prev ;
			this.next = next ;
		}
		void lock() {
			lock.lock();
		}
		void unlock() {
			lock.unlock();
		}
	}
	
	final Node<T> head ,tail ;
	
	ConcurrentSortedList(){
		head = new Node<>();
		tail = new Node<>();
		head.next = tail ;
		tail.prev = head ;
	}

	public boolean add(T value) {
		Node<T> cur = head;
		cur.lock();
		
		Node<T> next = head.next;
		try {
			while(true) {
				next.lock();
				try {
					if(next == tail || next.value.compareTo(value) > 0 ) {
						Node<T> node = new Node<>(value,cur,next);
						cur.next = node ;
						next.prev = node ;
						Console.tp("[%T][%t] add to list : %d\n",value);
						return true;
					}
				} finally {
					cur.unlock();
				}
				cur = next ;
				next = cur.next;
			}
		} finally {
			next.unlock();
		}
		//return false ;
	}


	public int size() {
		int cnt = 0 ;
		Node<T> cur = tail;
		ReentrantLock lock;
		while(true) {
			lock = cur.lock;
			lock.lock();
			try {
				if(cur.prev == head) {
					break;
				}
				cnt++ ;
				cur = cur.prev;
			} finally {
				lock.unlock();
			}
		}
		return cnt ;
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		ConcurrentSortedList<Integer> list = new ConcurrentSortedList<>();
		CcUt.start(()->{
			int n = 500 ;
			while(n-->0) {
				int val = RandomUt.random(0, 100);
				//Console.tp("[%t] add to list : %d\n",val);
				list.add(val);
				//Console.tp("[%T][%t] add to list : %d\n",val);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		CcUt.start(()->{
			int n = 500 ;
			while(n-->0) {
				Console.tp("[%T][%t] size = %d \n",list.size());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
}


