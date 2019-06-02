package com.zhangrui.rabbit.tranactional;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Desp: 发送方确认的形式实现生产者确认消息已经发布
 * 2019-06-02 23:33
 * Created by zhangrui.
 */
public class ConfirmSelectprovider {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("exchange_confirm", "topic", false, false, null);
        channel.queueDeclare("queue_confirm", false, false, false, null);
        channel.queueBind("queue_confirm", "exchange_confirm", "*.confirm.#");
        try {
            /**
             * 使用confirmSelect()之后，不会阻塞,但是是同步
             */
            channel.confirmSelect();
            channel.basicPublish("queue_confirm", "test.confirm", null, "测试事务".getBytes());
            int r = 1 / 0;
            
            if (!channel.waitForConfirms()){
                System.out.println("消息发送失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Thread.sleep(3000);

       // channel.close();
        connection.close();
    }
}
