package jd.demo.se.io.nio.buffer;


import java.nio.IntBuffer;
import java.util.Arrays;

public class IntBufferDemo {
    public static void main(String[] args) throws InterruptedException {
        IntBuffer buffer = IntBuffer.wrap(new int[]{1,2,3,4});
        VisualDemoBuffer v = new VisualDemoBuffer(buffer,true)
                .showOnFrame("IntBuffer demo",400,300)
                .run(()->buffer.put(5),()->buffer.flip());
    }
}
