package jd.demo.md.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class HelloActiveMQ {

    private static final String QUEUE_NAME = "TEST.FOO" ;
    private static ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
    private static Connection createConnection() throws JMSException {
        return factory.createConnection() ;
    }

    private static class ActiveMQProducer implements Runnable{

        public void run() {
            try {
                // Create a Connection
                Connection connection = createConnection();
                connection.start();

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue(QUEUE_NAME);

                // Create a MessageProducer from the Session to the Topic or Queue
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                // Create a messages
                String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
                TextMessage message = session.createTextMessage(text);

                // Tell the producer to send the message
                System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
                producer.send(message);

                // Clean up
                session.close();
                connection.close();
            }
            catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }
    }

    private static class ActiveMQConsumer implements Runnable, ExceptionListener {
        public void run() {
            try {
                // Create a Connection
                Connection connection = createConnection();
                connection.start();

                connection.setExceptionListener(this);

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue(QUEUE_NAME);

                // Create a MessageConsumer from the Session to the Topic or Queue
                MessageConsumer consumer = session.createConsumer(destination);

                // Wait for a message
                Message message = consumer.receive(1000);

                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("Received: " + text);
                } else {
                    System.out.println("Received: " + message);
                }

                consumer.close();
                session.close();
                connection.close();
            } catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }

        public synchronized void onException(JMSException ex) {
            System.out.println("JMS Exception occured.  Shutting down client.");
        }
    }

    public static void main(String[] args){
        thread(new ActiveMQProducer(),2);
        thread(new ActiveMQConsumer(),2);
    }

    private static void thread(Runnable r,int times){
        while(times-->0){
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.start();
        }
    }
}
