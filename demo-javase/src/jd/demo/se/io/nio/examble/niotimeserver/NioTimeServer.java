package jd.demo.se.io.nio.examble.niotimeserver;

import jd.util.lang.concurrent.CcUt;

import java.io.IOException;

public class NioTimeServer {

    public static final int PORT = 8100 ;
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(PORT);
        CcUt.start(timeServer,"NIO-MultiplexerTimeServer");
        CcUt.start(()->new TimeClientHandle(PORT),1);
    }
}
