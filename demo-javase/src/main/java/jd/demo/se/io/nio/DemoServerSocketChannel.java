package jd.demo.se.io.nio;

import static jd.demo.se.io.nio.NioServer.* ;
import jd.util.io.IOUt;
import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;
import org.omg.CORBA.TIMEOUT;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * reference: https://www.ibm.com/developerworks/java/tutorials/j-nio/j-nio-pdf.pdf
 */
class NioServer {

    public final static int PORT = 8100 ;
    // 缓冲区大小
    private static final int BUFFER_SIZE = 1024;
    // 超时时间，单位毫秒
    public static final int TIMEOUT = 3000;

    private static Selector register(int ...ports) throws IOException {
        // Create a new selector
        Selector selector = Selector.open();

        // Open a listener on each port, and register each one with the selector
        for (int port : ports) {
            // creates a new ServerSocketChannel, set to be non-blocking,and bind it to the given port
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking( false );
            ssc.socket().bind(new InetSocketAddress( port));
            SelectionKey key = ssc.register( selector, SelectionKey.OP_ACCEPT );
            System.out.println( "Going to listen on "+port );
        }
        return selector;
    }

    public static void start(BiConsumer<SocketChannel,ByteBuffer> consumer) throws IOException {
        try(Selector selector = register(PORT)){
            while (true) {
                if ( selector.select(TIMEOUT) == 0 ) continue;

                Iterator<SelectionKey> iterator  = selector.selectedKeys().iterator();
                while(iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isValid()) {
                        try{
                            if (key.isAcceptable()) {
                                // Accept the new connection
                                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                                SocketChannel sc = ssc.accept();
                                sc.configureBlocking(false);

                                // Add the new connection to the selector
                                SelectionKey newKey = sc.register(selector, SelectionKey.OP_READ);
                                //iterator.remove();
                                System.out.println("Got connection from " + sc);
                            } else if (key.isReadable()) {
                                // 获得与客户端通信的信道
                                SocketChannel clientChannel = (SocketChannel) key.channel();
                                // 得到并清空缓冲区
                                ByteBuffer buffer = (ByteBuffer) key.attachment();
                                buffer.clear();
                                // 读取信息获得读取的字节数
                                long bytesRead = clientChannel.read(buffer);
                                if (bytesRead == -1) {
                                    // 没有读取到内容的情况
                                    clientChannel.close();
                                } else if (bytesRead > 0){
                                    // 将缓冲区准备为数据传出状态
                                    buffer.flip();

                                    consumer.accept(clientChannel,buffer);

                                    // 设置为下一次读取或是写入做准备
                                    key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                                } // if bytesRead = 0 do nothing

                                // Read the data
                                SocketChannel sc = (SocketChannel) key.channel();

                                //iterator.remove();
                            }
                        }catch (Exception e){
                            IOUt.cancel(key);
                        }
                    }
                }
            }
        }
    }

    protected static void handlerInput(SocketChannel channel,ByteBuffer buffer)  {
        String receivedString = Charset.forName("UTF-8").decode(buffer).toString();
        System.out.printf("from %s receive: %s\n",channel.socket().getRemoteSocketAddress() ,receivedString);
        String sendString = "echo " + receivedString + "@" + new Date().toString();
        try {
            IOUt.write(channel,sendString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        NioServer.start(NioServer::handlerInput);
    }
}


class NIOClient implements Runnable{

    public NIOClient(int port){
        this.port = port;
    }

    public static void main(String[] args) throws IOException {
        NIOClient client = new NIOClient(PORT);
        CcUt.start(client,1);
    }

    public static final class SocketChannelCollectionFailException extends Throwable{
        SocketChannelCollectionFailException(int port){
            super("connect server at port " + port + " error " );
        }
    }
    private final int port ;

    public void run(){
        boolean stop = false ;
        try(Selector selector = Selector.open()) {
            ;
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            if(socketChannel.connect(new InetSocketAddress(PORT))){
                socketChannel.register(selector,SelectionKey.OP_READ);
                IOUt.write(socketChannel,"QUERY TIME ORDER");
                Console.tpln("[%t] write to server: Hello on connect");
            }else {
                socketChannel.register(selector,SelectionKey.OP_CONNECT);
                Console.tpln("[%t] register OP_CONNECT on connect");
            }
            while (!stop ){
                selector.select(TIMEOUT);
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if(key.isValid()) {
                        try{
                            SocketChannel channel = (SocketChannel) key.channel();
                            if(key.isConnectable()){
                                if(channel.finishConnect()){
                                    IOUt.write(socketChannel,"QUERY TIME ORDER");
                                    Console.tpln("[%t] write QUERY TIME ORDER order to the server when finished connection");
                                }else{
                                    throw new SocketChannelCollectionFailException(PORT);
                                }
                            }
                            if(key.isReadable()){
                                int readBytes = 0 ;
                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                if((readBytes = channel.read(byteBuffer)) >= 0){
                                    byteBuffer.flip();
                                    handlerInput(channel,byteBuffer);
                                } else if (readBytes < 0 ){
                                    key.cancel();
                                    channel.close();
                                    stop = true ;
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            IOUt.cancel(key);
                        } catch (SocketChannelCollectionFailException e) {
                            stop = true ;
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected static void handlerInput(SocketChannel channel,ByteBuffer byteBuffer) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        byteBuffer.flip();
        if(byteBuffer.remaining() > 0){
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);
            baos.write(bytes);
        }
        String msg = baos.toString();
        System.out.println("read from server : " + msg);
        IOUt.write(channel,"Hello");
        Console.tpln("[%t]write to server: Hello after read");
    }

}
public class DemoServerSocketChannel {

}
