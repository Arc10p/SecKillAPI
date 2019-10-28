package com.study.conchighperf;

import com.mysql.cj.util.TimeUtil;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MQtest {
    private static final String EXCHANGE_NAME="exchange_demo";
    private static final String ROUTING_KEY="KEY";
    private static final String QUEUE_NAME="queue";
    private static final String IP="192.168.47.129";
    private static final int PORT=5672;

    public static void  send() throws IOException, TimeoutException {
ConnectionFactory factory=new ConnectionFactory();
factory.setHost(IP);
factory.setPort(PORT);
factory.setUsername("root");
factory.setPassword("123456");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
    String msg = "hello world";

    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
    channel.close();
    connection.close();

    }
public static void receive() throws IOException, TimeoutException, InterruptedException {
    Address[] addresses=new Address[]{new Address(IP,PORT)};
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setUsername("root");
    connectionFactory.setPassword("123456");
    Connection connection=connectionFactory.newConnection(addresses);
    final Channel channel=connection.createChannel();
    channel.basicQos(64);
    //匿名内部类
    Consumer consumer=new DefaultConsumer(channel){
        @Override
        public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] bytes) throws IOException {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            channel.basicAck(envelope.getDeliveryTag(),false);
            System.out.println(new String(bytes));
        }
    };
    String msg=channel.basicConsume(QUEUE_NAME,consumer);


    TimeUnit.SECONDS.sleep(4);
    channel.close();
    connection.close();
}

public void RPCSend() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost(IP);
        connectionFactory.setPort(PORT);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("123456");
        Connection connection=connectionFactory.newConnection();
        Channel channel=connection.createChannel();
        String callbackQueueName=channel.queueDeclare().getQueue();


}
    public static void main(String[] args) {

        try {
            send();
            receive();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
