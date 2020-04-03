package jd.demo.ds.mq.rabbitmq.official.p1toc1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import jd.util.LoopUt;
import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;
import jd.util.lang.functional.OneArgAction;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static jd.demo.ds.mq.rabbitmq.common.DemoRabbitMqCommon.getFactory;


public class DemoSimplestProducer {

    protected static final String QUEUE_NAME = "Q1" ;
    protected static final String MSG = "hello-%d" ;
    protected static final String QUIT = "QUIT" ;

    private static Map<String,Boolean> receivedMap = new ConcurrentHashMap<>();
    private static CountDownLatch latch = new CountDownLatch(1);

    static {
        CcUt.startAtSameTime(2,DemoSimplestProducer::startReceiver);
    }

    public static void main(String[] args) throws Throwable {
        startProduce(10);
        latch.await();
        System.out.println("assert : " +!receivedMap.containsValue(Boolean.FALSE));
    }

    public static void startReceiver() throws Throwable {
        doIt((channel)->{
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            channel.basicConsume(QUEUE_NAME,true,(consumerTag, delivery)->{
                String message = new String(delivery.getBody());
                Console.tpln("[%t]Received '" + message + "'");
                if(QUIT.equals(message)){
                    latch.countDown();
                    //return;
                }else {
                    receivedMap.put(message,true);
                }
            },consumerTag->{
                Console.tpln(consumerTag);
            });
        });
    }

    public static void startProduce(final int loop) throws Throwable {
        doIt((channel)->{
            LoopUt.rangeFrom0Num(loop,i->{
                try {
                    String msg = String.format(MSG ,i);
                    channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
                    receivedMap.put(msg,false);
                    //Thread.sleep(100);
                } catch (IOException  e) {
                    e.printStackTrace();
                }
            });
            channel.basicPublish("",QUEUE_NAME,null,QUIT.getBytes());
        });
    }


    private static void doIt(OneArgAction<Channel> action) throws Throwable {
        // 获取connection及channel
        Connection connection = getFactory().newConnection();
        Channel channel = connection.createChannel();
        // 声明queue
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 执行动作
        action.action(channel);
        // 关闭channel及connection
        channel.close();
        connection.close();
    }
}
