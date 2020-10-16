package jd.demo.se.concurrent.basic.atomic;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;

public class DemoAtomicReference {

    @Test
    public void demoAtomic(){
        AtomicReference reference = new AtomicReference();
        String value = UUID.randomUUID().toString() ;
        reference.set(value);
        Runnable r = ()->{
            String newVal = UUID.randomUUID().toString();
            reference.compareAndSet(newVal,value);
            Assert.assertEquals(newVal,reference.get());
        };
        ExecutorService threadpool = Executors.newFixedThreadPool(8,Thread::new);
        int i = 10 ;
        while(i-->0){
            threadpool.submit(r);
        }

    }
}
