package jd.ds.mq.rabbitmq.official.p1toc1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import jd.ds.mq.rabbitmq.official.common.DemoRabbitMqCommon;
import jd.util.LoopUt;
import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;
import jd.util.lang.functional.IntObjConsumer;
import jd.util.lang.functional.NoArgsAction;
import jd.util.lang.functional.OneArgAction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

import static jd.ds.mq.rabbitmq.official.common.DemoRabbitMqCommon.* ;

public class DemoSimplestProducer {

    protected static final String QUEUE_NAME = "Q1" ;
    protected static final String MSG = "hello-%d" ;
    protected static final String QUIT = "QUIT" ;

    static Map<String,Boolean> receivedMap = new ConcurrentHashMap<>();
    static CountDownLatch latch = new CountDownLatch(1);

    @BeforeClass
    public static void setReceiver(){
        CcUt.startAtSameTime(2,()-> {
            try {
                receive();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @Test
    public void test() throws Throwable {
        produce(10);
        latch.await();
        Assert.assertTrue(!receivedMap.containsValue(Boolean.FALSE));
    }

    public static void receive() throws Throwable {
        doIt((channel)->{
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            QueueingConsumer consumer = new QueueingConsumer(channel);
            // 监听队列
            channel.basicConsume(QUEUE_NAME, true, consumer);
            // 获取消息
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                Console.tpln("[%t]Received '" + message + "'");
                if(QUIT.equals(message)){
                    latch.countDown();
                    break;
                }else {
                    receivedMap.put(message,true);
                }
            }
        });
    }

    public void produce(final int loop) throws Throwable {
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
        Connection connection = getFactory().newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        action.action(channel);
        channel.close();
        connection.close();
    }
}
