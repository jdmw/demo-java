package jd.demo.mq.racketmq.simplemsg;

import jd.demo.mq.racketmq.RacketMqDemoHelper;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public class SynchSender {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = RacketMqDemoHelper.defaultProducer();
        RacketMqDemoHelper.send(producer,100,i->{
            Message msg = RacketMqDemoHelper.newMessage("TagA","BroadcastProducer-"+i);
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        });
    }
}
