package com.zhangrui.rabbit.direct;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Desp:
 * 2018-03-26 21:36
 * Created by zhangrui.
 */
public class ConsumerA {

    private final static String QUEUE_NAME = "rabbitMQ";

    private final static String[] routeKeys = {"warn", "info"};

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare("my-exchange-1", "direct");


        System.out.println("获取消息");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        };


        for (String routeKey : routeKeys) {
            channel.queueDeclare(routeKey + "-" + QUEUE_NAME, false, false, false, null);
            channel.queueBind(routeKey + "-" + QUEUE_NAME, "my-exchange-1", routeKey);
            channel.basicConsume(routeKey + "-" + QUEUE_NAME, true, consumer);
        }




    }
}
