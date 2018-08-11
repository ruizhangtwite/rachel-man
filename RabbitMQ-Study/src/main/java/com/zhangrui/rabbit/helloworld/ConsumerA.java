package com.zhangrui.rabbit.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Desp:
 * 2018-03-26 20:22
 * Created by zhangrui.
 */
public class ConsumerA {

    private final static String QUEUE_NAME = "rabbitMQ";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare("my-exchange", "fanout");
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, "my-exchange", "");

        System.out.println("获取消息");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}
