package jd.demo.se.bypkg.lang.ref;

import jd.util.SysUt;
import jd.util.lang.concurrent.CcUt;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DemoWeakReference {

    // memory at start:    2079712 ->
    //   when finished: 1025662584
    @Test
    public void demo() throws InterruptedException {
        final List list = new ArrayList();
        long memory = SysUt.gcAndGetMemory();
        CcUt.run(()->list.add(new byte[102400]) ,  100);
        System.out.println("strong reference test  menory:  " +memory + " -> " + SysUt.gcAndGetMemory());

        list.clear();
        memory = SysUt.gcAndGetMemory();
        CcUt.run(()->list.add(new WeakReference<>(new byte[102400])) ,  100);
        System.out.println("strong reference test  menory:  " +memory + " -> " + SysUt.gcAndGetMemory());
    }

}
