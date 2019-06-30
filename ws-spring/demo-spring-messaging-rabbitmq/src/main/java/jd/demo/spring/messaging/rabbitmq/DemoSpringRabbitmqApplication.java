package jd.demo.spring.messaging.rabbitmq;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoSpringRabbitmqApplication {

    static final String TOPIC_EXCHAGE_NAME = "spring-boot-exchange" ;
    static final String QUEUE_NAME = "spring-boot-queue" ;
    @Bean
    Queue queue(){
        return new Queue(QUEUE_NAME,false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(TOPIC_EXCHAGE_NAME);
    }

    @Bean
    Binding binding(Queue queue,TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory factory, MessageListenerAdapter listenerAdapter){
        System.out.println(factory.getHost());
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage");
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DemoSpringRabbitmqApplication.class, args);
        TimeUnit.SECONDS.sleep(10);
    }

}





