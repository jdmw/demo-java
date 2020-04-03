package jd.demo.ds.mq.rabbitmq.official;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jd.demo.ds.mq.rabbitmq.common.DemoRabbitMqCommon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static jd.demo.ds.mq.rabbitmq.common.DemoRabbitMqCommon.HOST;
import static jd.demo.ds.mq.rabbitmq.common.DemoRabbitMqCommon.getFactory;

public class Hello {

    private final static int PRODUCER_NUM = 1 ;
    private final static int CONSUMER_NUM = 1 ;
    private final static int PACKAGE_NUM = 1 ;

    protected final static String QUEUE_NAME = "hello" ;
    protected final static String MSG_FMT = "Hello-%d-%0" + String.valueOf(PACKAGE_NUM).length() +"d" ;
    protected static final String QUIT = "QUIT" ;

    static ConnectionFactory factory = getFactory();
    private static Map<String,Boolean> receivedMap = new ConcurrentHashMap<>();
    static {
        factory.setHost(HOST);
    }

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(PRODUCER_NUM);
        receive(CONSUMER_NUM, latch);
        send(PRODUCER_NUM, PACKAGE_NUM);
        latch.await();
        System.out.println("assert : " +!receivedMap.containsValue(Boolean.FALSE));
        System.exit(0);
    }

    public static int send(int number,int count) throws Exception{
        for(int i=0;i<number;i++){
            try(Connection connection = factory.newConnection(); Channel channel = connection.createChannel()){
                for(int j=0;j<count;j++) {
                    String msg = String.format(MSG_FMT, i, j);
                    channel.queueDeclare(QUEUE_NAME,false,false,false,null);
                    channel.basicPublish("",QUEUE_NAME,null,msg.getBytes(DemoRabbitMqCommon.DEFAULT_CHARSET));
                    System.out.println( "Sent     : " + msg  + "\t| " + Thread.currentThread().getName());
                    receivedMap.put(msg,false);
                }
                channel.basicPublish("",QUEUE_NAME,null,QUIT.getBytes());
            }
        }
        return number * count;
    }

    public static void receive(int number, CountDownLatch quitSignal) throws Exception{
        /*ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(DemoRabbitMqHelper.HOST);*/
        for(int i=0;i<number;i++){
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            channel.basicConsume(QUEUE_NAME,true,(consumerTag, delivery)->{
                String message = new String(delivery.getBody(), DemoRabbitMqCommon.DEFAULT_CHARSET);
                System.out.println( "Received : " + message + "\t| " + Thread.currentThread().getName());
                if(QUIT.equals(message)){
                    quitSignal.countDown();
                }else {
                    receivedMap.put(message,true);
                }
            },consumerTag->{});
        }
    }
}
