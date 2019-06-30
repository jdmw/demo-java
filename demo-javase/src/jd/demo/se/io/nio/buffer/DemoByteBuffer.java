package jd.demo.se.io.nio.buffer;

import jd.util.ui.swing.ComponentUtil;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;
import static jd.demo.se.io.nio.buffer.VisualDemoBuffer.getBufferInfo ;

public class DemoByteBuffer {


    @Test
    public void demo1() throws InterruptedException {

        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(getBufferInfo(buffer));
        buffer.put(1,(byte)1);
        System.out.println(getBufferInfo(buffer));
        buffer.put(2,(byte)2);
        System.out.println(getBufferInfo(buffer));
        System.out.println("buffer.get(1)=" + buffer.get(1));
        System.out.println(getBufferInfo(buffer));
        System.out.println("buffer.get()=" + buffer.get());
        System.out.println(getBufferInfo(buffer));
        VisualDemoBuffer v = new VisualDemoBuffer(buffer);
        ComponentUtil.show("demo buffer",v,500,400).setAlwaysOnTop(true);
        v.run(2000,
                ()->buffer.put(1,(byte)3),
                ()->buffer.put(2,(byte)4),
                ()->System.out.println("buffer.get(1)="+ buffer.get(1)));
        System.out.println("buffer.array()" + Arrays.toString(buffer.array()));
    }

    @Test
    public void demoArray(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.array().length);
    }
}
