package jd.demo.se.collections;

import jd.util.SysUt;
import jd.util.lang.concurrent.CcUt;
import org.junit.Assert;
import org.junit.Test;

import java.util.WeakHashMap;

public class DemoWeakHashMap {

    @Test
    public void demo(){
        WeakHashMap<String,byte[]> map = new WeakHashMap<>();
        long memory = SysUt.gcAndGetMemory();
        CcUt.run(i->map.put(i+"",new byte[10240]),100);
        System.out.println("test WeakHashMap : memory  " +memory + " -> " + SysUt.gcAndGetMemory());
        Assert.assertEquals(0,map.size());
    }
}
