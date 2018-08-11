package com.zhangrui.rabbit.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Desp:
 * 2018-03-26 21:36
 * Created by zhangrui.
 */
public class Provider1 {

    private final static String QUEUE_NAME = "rabbitMQ";

    private final static String[] routeKeys = {"error", "warn", "info"};

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("my-exchange-1", "direct");

        for (String routeKey : routeKeys) {
            channel.queueDeclare(routeKey + "-" + QUEUE_NAME, false, false, false, null);
            channel.queueBind(routeKey + "-" + QUEUE_NAME, "my-exchange-1", routeKey);
            channel.basicPublish("my-exchange-1", routeKey, null, ("我是" + routeKey + "级别").getBytes());
        }

        System.out.println("消息已经发送");

        channel.close();
        connection.close();
    }

}
