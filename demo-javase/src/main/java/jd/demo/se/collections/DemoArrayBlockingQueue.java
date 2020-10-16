package jd.demo.se.collections;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

public class DemoArrayBlockingQueue {

    @Test
    public void demoEnque(){
        Runnable r = () -> System.out.println("This is a Runnable");
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        int i=10;
        while (i-->0){
            queue.add(r);
        }
        System.out.println("queue's size is " + queue.size());
    }
}
