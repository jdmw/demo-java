package jd.demo.se.concurrent.basic;

import jd.util.lang.concurrent.CcUt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 图解ReentrantReadWriteLock实现分析:
 * https://segmentfault.com/a/1190000015768003
 */
public class DemoReentrantReadWriteLock {

    public static class RwDict<K,V> extends TreeMap<K,V> {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        @Override
        public V get(Object key){
            lock.readLock().lock();
            try{
                return super.get(key);
            }finally {
                lock.readLock().unlock();
            }
        }

        public Collection<K> keys(){
            lock.readLock().lock();
            try{
                return new ArrayList<>(super.keySet());
            }finally {
                lock.readLock().unlock();
            }
        }

        @Override
        public V put(K key,V data){
            lock.writeLock().lock();
            try{
                return super.put(key,data);
            }finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public void clear(){
            lock.writeLock().lock();
            try{
                super.clear();
            }finally {
                lock.writeLock().unlock();
            }
        }

    }


    public static void main(String[] args){
        RwDict<String,Object> d = new RwDict<>();
        CcUt.start(()->()->{
            while (true){
                System.out.println(d.get("A"));
                CcUt.sleep(1, TimeUnit.SECONDS);
            }

        },5);

        CcUt.startInLoop(true,1000L,()->{d.put("A", System.currentTimeMillis());});


    }
}
