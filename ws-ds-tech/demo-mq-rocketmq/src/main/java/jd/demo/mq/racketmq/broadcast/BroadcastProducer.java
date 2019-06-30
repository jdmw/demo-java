package jd.demo.mq.racketmq.broadcast;

import jd.demo.mq.racketmq.RacketMqDemoHelper;
import jd.demo.mq.racketmq.simplemsg.SynchSender;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class BroadcastProducer {
    public static void main(String[] args) throws Exception {
        SynchSender.main(args);
    }
}
