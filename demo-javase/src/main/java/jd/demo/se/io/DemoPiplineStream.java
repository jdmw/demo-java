package jd.demo.se.io;

import com.alibaba.fastjson.JSON;
import jd.demo.example.shop.Goods;
import jd.util.lang.concurrent.CcUt;
import jd.util.lang.math.RandomUt;
import lombok.Getter;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 管道流用于多个线程之间传递信息，若用在同一个线程中可能会造成死锁；
 管道流的输入输出是成对的，一个输出流只能对应一个输入流，使用构造函数或者connect函数进行连接；
 一对管道流包含一个缓冲区，其默认值为1024个字节，若要改变缓冲区大小，可以使用带有参数的构造函数；
 管道的读写操作是互相阻塞的，当缓冲区为空时，读操作阻塞；当缓冲区满时，写操作阻塞；
 管道依附于线程，因此若线程结束，则虽然管道流对象还在，仍然会报错“read dead end”；
 管道流的读取方法与普通流不同，只有输出流正确close时，输出流才能读到-1值。
 */
public class DemoPiplineStream {

    @Getter
    public static class Producer implements Runnable{

        private PipedOutputStream outputStream = new PipedOutputStream();
        @Override
        public void run() {
            int i = 100 ;
            while (i-->0){
                Goods goods = new Goods(UUID.randomUUID().toString(), RandomUt.random(1,5));
                goods.setNum(1);
                try {
                    Thread.sleep(100);
                    goods.setDescription("create at " + new Date() );
                    outputStream.write(JSON.toJSONString(goods).getBytes());
                    outputStream.write("\n".getBytes());
                    outputStream.flush();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Getter
    public static class Consumer implements Runnable{

        private PipedInputStream inputStream = new PipedInputStream();
        public Consumer(PipedOutputStream outputStream) throws IOException {
            inputStream = new PipedInputStream();
            inputStream.connect(outputStream);
        }
        @Override
        public void run() {
            byte[] buf = new byte[1024];
            while (true){
                try {
                    // 通过read方法 读取长度
                    int len = inputStream.read(buf);
                    if(len > 0 ){
                        System.out.printf(new String(buf, 0, len));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Producer producer = new Producer();
        Consumer consumer = new Consumer(producer.getOutputStream());
        CcUt.start(producer,producer.getClass().getSimpleName());
        CcUt.start(consumer,consumer.getClass().getSimpleName());
    }
}
