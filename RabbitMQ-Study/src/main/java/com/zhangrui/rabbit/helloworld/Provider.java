package com.zhangrui.rabbit.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Desp:
 * 2018-03-26 20:22
 * Created by zhangrui.
 */
public class Provider {

    private final static String QUEUE_NAME = "rabbitMQ";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("my-exchange", "fanout");
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, "my-exchange", "");
        channel.basicPublish("my-exchange", "", null, "你好".getBytes());

        System.out.println("消息已经发送");

        channel.close();
        connection.close();
    }

}
