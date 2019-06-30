package jd.demo.se.io.nio.buffer;

import jd.util.ui.swing.ComponentUtil;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import static jd.demo.se.io.nio.buffer.VisualDemoBuffer.getBufferInfo;

public class DemoCharBuffer {
    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {

        String charset = "UTF-16" ;
        ByteBuffer buffer = ByteBuffer.wrap("Hello".getBytes(charset));
        //Charset.availableCharsets().forEach((k,v)->System.out.println(v));
        System.out.println(new String(Charset.forName(charset).decode(buffer).array()));
    }
}
