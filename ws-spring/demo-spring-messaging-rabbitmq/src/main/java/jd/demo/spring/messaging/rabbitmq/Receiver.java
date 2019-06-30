package jd.demo.spring.messaging.rabbitmq;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

    // @RabbitListener(queues = "hello.queue1")
    public String receiveMessage(String msg) {
        System.out.println(Thread.currentThread().getName() + " 接收到来自队列的消息：" + msg);
        return msg.toUpperCase();
    }


}