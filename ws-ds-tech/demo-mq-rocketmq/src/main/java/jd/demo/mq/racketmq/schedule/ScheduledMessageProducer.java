package jd.demo.mq.racketmq.schedule;

import jd.demo.mq.racketmq.RacketMqDemoHelper;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = RacketMqDemoHelper.defaultProducer();
        RacketMqDemoHelper.send(producer,100,i->{
            Message msg = RacketMqDemoHelper.newMessage("TagA","BroadcastProducer-"+i);
            if(i>50) msg.setDelayTimeLevel(3);
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        });
    }
}
