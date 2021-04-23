package jd.demo.se.concurrent.basic;

import jd.util.Assert;
import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;
import jd.util.lang.math.RandomUt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DemoLock {
    private static final int RANGE = 100 ;

    private static abstract class Action{
        protected final List<Integer> list = new ArrayList<>();
        //private static volatile boolean init = false ;
        protected int count(Integer i ){
            return (int)list.stream().filter(n->n==i).count() ;
        }
        //protected static boolean isInit(){
        //    return init ;
        //}
        protected void doInit(){
            Console.tpln("[%t]doInit()");
            for (int i = 0; i < RANGE; i++) {
                list.add(i);
            }
        }
    }


    private static class DemoLockUsingSynchronized extends Action{
        private static volatile boolean init = false ;
        public int count(Integer i ){
            if(!init ){
                synchronized (DemoLock.class){
                    Console.tpln("[%t]count--enter lock,init=" + init);
                    if(!init){
                        CcUt.sleep(1000);
                        doInit();
                    }
                    Console.tpln("[%t]count--leave lock,init=" + init);
                    init = true ;
                }
            }
            return super.count(i);
        }
    }

    private static class DemoLockUsingReenterLock extends Action{
        private static volatile boolean init = false ;
        private static final ReentrantLock lock = new ReentrantLock();
        public int count(Integer i ){
            if(!init ){
                try {
                    if(lock.tryLock(1, TimeUnit.MINUTES)){
                        if(!init){
                            Console.tpln("[%t]count--enter lock,init=" + init);
                            CcUt.sleep(1000);
                            doInit();
                            Console.tpln("[%t]count--leave lock,init=" + init);
                            init = true ;
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
            }
            return super.count(i);
        }
    }

    private static class DemoLockUsingLatch extends Action{
        private static volatile boolean init = false ;
        private static final ReentrantLock lock = new ReentrantLock();
        CountDownLatch latch = new CountDownLatch(1); ;
        public int count(Integer i ){
            if(latch.getCount() > 0){
                Console.tpln("[%t]count--enter lock,init=" + init);
                CcUt.sleep(1000);
                doInit();
                Console.tpln("[%t]count--leave lock,init=" + init);
                latch.countDown();
            }
            return super.count(i);
        }
    }

    public static void main(String[] args){
        Action d2 = new DemoLockUsingSynchronized();
        Action d3 = new DemoLockUsingReenterLock();
        Action d1 = new DemoLockUsingLatch();
        try{
            CcUt.startAtSameTime(false,50,()->{
                int i = RandomUt.random(1, RANGE);
                Assert.isTrue(d1.count(i) == 1);
                Assert.isTrue(d1.list.size() == RANGE);
            });
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
}
