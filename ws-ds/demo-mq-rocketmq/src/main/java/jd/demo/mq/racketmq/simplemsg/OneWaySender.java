package jd.demo.mq.racketmq.simplemsg;

import jd.demo.mq.racketmq.RacketMqDemoHelper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;


public class OneWaySender {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = RacketMqDemoHelper.defaultProducer();
        producer.start();
        int i = 100;
        while (i-->0){
            Message msg = RacketMqDemoHelper.newMessage("TAG1","OneWaySender-"+i);
            producer.sendOneway(msg);
        }
        System.out.println("messages sent");
        producer.shutdown();

    }
}
