package jd.demo.mq.racketmq.simplemsg;

import jd.demo.mq.racketmq.RacketMqDemoHelper;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public class AsynchSender {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        System.setProperty("autoCreateTopicEnable","true");
        DefaultMQProducer producer = new DefaultMQProducer(RacketMqDemoHelper.DEFAULT_GROUP_NAME);
        producer.setNamesrvAddr(RacketMqDemoHelper.NAME_SERVER_ADDRESS);
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(1);
        for(int i=0;i<10;i++){
            final int index = i ;
            Message msg = new Message(RacketMqDemoHelper.DEFAULT_TOPIC,"Tag1",("Hello "+1).getBytes(RacketMqDemoHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                public void onSuccess(SendResult sendResult) {
                    //System.out.printf("index=%d send %s success \n",index,sendResult.getMsgId());
                    System.out.printf("send %-10d OK %s %n", index, sendResult.getMsgId());
                }
                public void onException(Throwable e) {
                    System.err.printf("send %-10d error %s %n", index, e.getMessage());
                }
            });
        }
        Thread.sleep(100);
        producer.shutdown();
    }
}
