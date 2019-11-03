package jd.ds.mq.rabbitmq.official.common;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;


public class DemoRabbitMqCommon {

    public static final String HOST = "localhost" ;

    private DemoRabbitMqCommon(){}

    public static ConnectionFactory getFactory() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        // factory.setPort(5672);
        //  factory.setVirtualHost("testhost");
        //  factory.setUsername("admin");
        //  factory.setPassword("admin");
        return  factory;
    }

}
