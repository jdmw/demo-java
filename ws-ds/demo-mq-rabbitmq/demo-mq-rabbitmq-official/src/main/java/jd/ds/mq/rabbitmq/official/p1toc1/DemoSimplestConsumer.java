package jd.ds.mq.rabbitmq.official.p1toc1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import jd.ds.mq.rabbitmq.official.common.DemoRabbitMqCommon;

import java.util.concurrent.CountDownLatch;

import static jd.ds.mq.rabbitmq.official.p1toc1.DemoSimplestProducer.QUEUE_NAME;


