package jd.demo.spring.messaging.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate template;
    private final Receiver receiver ;
    public Runner(RabbitTemplate template, Receiver receiver) {
        this.template = template;
        this.receiver = receiver;
    }

    @Override
    public void run(String... args) throws Exception {
        template.convertAndSend(DemoSpringRabbitmqApplication.TOPIC_EXCHAGE_NAME,"hello","Hello");
    }
}
