package com.zhangrui.rabbit.queues;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Desp:
 * 2018-03-26 20:55
 * Created by zhangrui.
 */
public class C1 {

    private final static String QUEUE_NAME = "rabbit";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 每次从队列中获取数量
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                doWork(new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer);
    }


    private static void doWork(String body) {
        try {
            System.out.println(body);
            System.out.println("Done");
        } finally {

        }
    }
}
