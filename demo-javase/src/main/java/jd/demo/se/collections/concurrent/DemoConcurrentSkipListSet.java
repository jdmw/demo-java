package jd.demo.se.collections.concurrent;

import jd.util.lang.concurrent.CcUt;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.ConcurrentSkipListSet;

public class DemoConcurrentSkipListSet {

    @Test
    public void demo() throws InterruptedException {
        int times = 100 ;
        ConcurrentSkipListSet<Long> set = new ConcurrentSkipListSet<Long>();
        CcUt.start(()->{/*System.out.println("add date");*/set.add(System.nanoTime());},times);
        Thread.currentThread().join();
        Assert.assertEquals(times,set.size());
    }
}
