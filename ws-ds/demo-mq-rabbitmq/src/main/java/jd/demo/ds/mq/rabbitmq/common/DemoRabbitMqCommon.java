package jd.demo.ds.mq.rabbitmq.common;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;


public class DemoRabbitMqCommon {

    public static final String HOST = "localhost" ;
    public static final int PORT = 5672 ;// default port: 5672
    /*public final static String DEFAULT_GROUP_NAME = "default_group";
    public final static String NAME_SERVER_ADDRESS = "localhost:9876";
    public final static String DEFAULT_TOPIC = "TopicTest";*/
    public final static String DEFAULT_CHARSET = "UTF-8";
    public final static String DEFAULT_QUEUE_NAME = "hello" ;

    private DemoRabbitMqCommon(){}

    public static ConnectionFactory getFactory()  {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        // factory.setPort(5672);
        //  factory.setVirtualHost("testhost");
        //  factory.setUsername("admin");
        //  factory.setPassword("admin");
        return  factory;
    }

}
