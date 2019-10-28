package com.study.conchighperf.MQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class ProducerTemplate {
    private static final String EXCHANGE_NAME="exchange_demo";
    private static final String ROUTING_KEY="KEY";
    private static final String QUEUE_NAME="queue";
    private static final String IP="192.168.47.129";
    private static final int PORT=5672;
    private Channel channel;
    {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(IP);
        factory.setPort(PORT);
        factory.setUsername("root");
        factory.setPassword("123456");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            this.channel=channel;

        }catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public Channel getChannel(){
        return channel;
    }

    public void Send(byte[] msg){
        try {
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
