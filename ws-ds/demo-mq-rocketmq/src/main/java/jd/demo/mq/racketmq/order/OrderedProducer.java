package jd.demo.mq.racketmq.order;

import jd.demo.mq.racketmq.RacketMqDemoHelper;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;


public class OrderedProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = RacketMqDemoHelper.defaultProducer();
        String[] tags = new String[]{"TAG-A","TAG-B","TAG-C","TAG-D","TAG-E"} ;
        RacketMqDemoHelper.send(producer,100000,i->{
            Message msg = RacketMqDemoHelper.newMessage(tags[i%tags.length],"OrderedProducer-"+i);
            try {
                producer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        int index = ((int) arg) % mqs.size();
                        return mqs.get(index);
                    }
                },i%10);
                System.out.printf("send msg %d\n",i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
