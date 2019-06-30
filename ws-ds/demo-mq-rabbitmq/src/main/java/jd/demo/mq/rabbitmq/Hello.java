package jd.demo.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jd.demo.mq.rabbitmq.DemoRabbitMqHelper;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.CountDownLatch;

public class Hello {
    protected final static String QUEUE_NAME = "hello" ;
    protected final static String MSG_FMT = "Hello-%d-%02d" ;

    static ConnectionFactory factory = new ConnectionFactory();
    static {
        factory.setHost(DemoRabbitMqHelper.HOST);
    }

    public static void main(String[] args) throws Exception {
        receive(3,new CountDownLatch(send(3,10)));
        System.exit(0);
    }

    public static int send(int number,int count) throws Exception{
        /*ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(DemoRabbitMqHelper.HOST);*/
        for(int i=0;i<number;i++){
            for(int j=0;j<count;j++){
                String msg = String.format(MSG_FMT ,i,j);
                try(Connection connection = factory.newConnection(); Channel channel = connection.createChannel()){
                    channel.queueDeclare(QUEUE_NAME,false,false,false,null);
                    channel.basicPublish("",QUEUE_NAME,null,msg.getBytes(DemoRabbitMqHelper.DEFAULT_CHARSET));
                    System.out.println( "Sent     : " + msg  + "\t| " + Thread.currentThread().getName());
                }catch (Exception e){
                    throw e ;
                }
            }
        }
        return number * count;
    }

    public static void receive(int number, CountDownLatch latch) throws Exception{
        /*ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(DemoRabbitMqHelper.HOST);*/

        for(int i=0;i<number;i++){
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            channel.basicConsume(QUEUE_NAME,true,(consumerTag, delivery)->{
                System.out.println( "Received : " + new String(delivery.getBody(),DemoRabbitMqHelper.DEFAULT_CHARSET)
                        + "\t| " + Thread.currentThread().getName());
                latch.countDown();
            },consumerTag->{});
        }
        latch.await();
    }
}
