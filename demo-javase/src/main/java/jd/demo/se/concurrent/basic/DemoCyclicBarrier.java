package jd.demo.se.concurrent.basic;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;
import jd.util.lang.math.RandomUt;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by huangxia on 2008/4/23.
 */
public class DemoCyclicBarrier {

    public static void main(String[] args) {
        int threadNum = 5;
        CyclicBarrier barrier = new CyclicBarrier(threadNum, ()-> {
            System.out.println(Thread.currentThread().getName() + " 完成最后任务");
        });

        while (threadNum-->0) {
            CcUt.start(() -> {
                try {
                    Thread.sleep(RandomUt.random(40,100));
                    Console.tpln("[%t]到达栅栏 A");
                    barrier.await();
                    Console.tpln("[%t]冲破栅栏 A");

                    Thread.sleep(RandomUt.random(40,100));
                    Console.tpln("[%t]到达栅栏 B");
                    barrier.await();
                    Console.tpln("[%t]冲破栅栏 B");

                    Thread.sleep(RandomUt.random(40,100));
                    Console.tpln("[%t]到达栅栏 C");
                    barrier.await();
                    Console.tpln("[%t]冲破栅栏 C");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, false);
        }

        // TODO 遗留问题: CyclicBarrier 与线程池结合时,运行异常,无法正常执行结束
    }
}
