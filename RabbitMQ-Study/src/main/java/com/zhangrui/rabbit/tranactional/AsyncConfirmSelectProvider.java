package com.zhangrui.rabbit.tranactional;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Desp: 生产者发送消息确认（异步）
 * 2019-06-02 23:38
 * Created by zhangrui.
 */
public class AsyncConfirmSelectProvider {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("exchange_confirm_async", "topic", false, false, null);
        channel.queueDeclare("queue_confirm_async", false, false, false, null);
        channel.queueBind("queue_confirm_async", "exchange_confirm_async", "*.confirm.#");

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(deliveryTag + "has ack");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(deliveryTag + "has noACK!!");
            }
        });

        try {
            /**
             * 使用confirmSelect()之后，不会阻塞,但是是同步
             */
            channel.confirmSelect();
            channel.basicPublish("queue_confirm_async", "test.confirm", null, "测试事务".getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }


        Thread.sleep(3000);
        
        
        channel.clearConfirmListeners();
        channel.close();
        connection.close();
    }
}
