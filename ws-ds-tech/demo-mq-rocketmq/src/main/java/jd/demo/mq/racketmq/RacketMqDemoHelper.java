package jd.demo.mq.racketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.io.UnsupportedEncodingException;
import java.util.function.IntConsumer;

public class RacketMqDemoHelper {
    public final static String DEFAULT_GROUP_NAME = "default_group";
    public final static String NAME_SERVER_ADDRESS = "localhost:9876";
    public final static String DEFAULT_TOPIC = "TopicTest";
    public final static String DEFAULT_CHARSET = "UTF-8";

    public static DefaultMQProducer defaultProducer() {
        DefaultMQProducer producer = new DefaultMQProducer(DEFAULT_GROUP_NAME);
        producer.setNamesrvAddr(NAME_SERVER_ADDRESS);
        return producer;
    }

    public static Message newMessage(String tag, CharSequence content) {
        Message msg = null;
        try {
            msg = new Message(DEFAULT_TOPIC, tag, content.toString().getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static interface Action {
        void action(int i) throws Exception;
    }


    public static void send(DefaultMQProducer producer, int times, Action action) throws Exception {
        producer.start();
        for (int i = 0; i < times; i++) {
            action.action(i);
        }
        producer.shutdown();
    }
}
