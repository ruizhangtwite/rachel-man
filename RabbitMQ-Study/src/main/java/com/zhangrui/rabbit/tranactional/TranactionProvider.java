package com.zhangrui.rabbit.tranactional;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Desp: Transaction的操作
 * 2019-06-02 23:26
 * Created by zhangrui.
 */
public class TranactionProvider {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("exchange_transaction", "topic", false, false, null);
        channel.queueDeclare("queue_transaction", false, false, false, null);
        channel.queueBind("queue_transaction", "exchange_transaction", "*.transaction.#");
        try {
            /**
             * 使用txSelect()之后，当调用发送消息的basicPublish()方法时，是会阻塞的，因此不建议使用
             * 而是用发送方确认的形式（同步或者异步）
             */
            channel.txSelect();
            channel.basicPublish("queue_transaction", "test.transaction", null, "测试事务".getBytes());
            int r = 1 / 0;
            channel.txCommit();
        } catch (Exception e) {
            e.printStackTrace();
            channel.txRollback();
        }


        Thread.sleep(3000);

        channel.close();
        connection.close();
    }
}
