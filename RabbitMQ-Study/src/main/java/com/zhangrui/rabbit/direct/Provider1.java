package com.zhangrui.rabbit.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

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
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("handleAck---");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("handleNack---");
            }
        });

        for (String routeKey : routeKeys) {
            channel.queueDeclare(routeKey + "-" + QUEUE_NAME, false, false, false, null);
            channel.queueBind(routeKey + "-" + QUEUE_NAME, "my-exchange-1", routeKey);
            channel.basicPublish("my-exchange-1", routeKey, null, ("我是" + routeKey + "级别").getBytes());
            channel.confirmSelect();
        }

        System.out.println("消息已经发送");
        
        channel.clearConfirmListeners();
        channel.close();
        connection.close();
    }

}
