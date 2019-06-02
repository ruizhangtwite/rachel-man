package com.zhangrui.rabbit.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Desp: 死信队列
 * 死信队列是指
 * 1.当某个队列中的消息被拒绝，但是requeue设置为false
 * 2.消息超时
 * 3.队列的长度满了
 * 
 * 则会将消息路由到对应的死信队列中
 * 2019-06-02 23:06
 * Created by zhangrui.
 */
public class DlxProvider {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.exchangeDeclare("exchange_normal", "topic", false, false, null);
        channel.exchangeDeclare("exchange_dlx", "direct", false, false, null);
        
        Map<String, Object> map = new HashMap<>();
        /**
         * 设置消息的超时时间，单位毫秒
         */
        map.put("x-message-ttl", 10000);
        /**
         * 设置死信队列名称
         */
        map.put("x-dead-letter-exchange", "exchange_dlx");
        /**
         * 设置死信队列的routingKey,如果不设置，默认为原队列的路由键
         */
        map.put("x-dead-letter-routing-key", "dead.letter");
        /**
         * 设置这个队列的死信特性
         */
        channel.queueDeclare("queue_normal", false, false, false, map);
        channel.queueBind("queue_normal", "exchange_normal", "*.letter.#");
        
        
        channel.queueDeclare("queue_dlx", false, false, false, null);
        channel.queueBind("queue_dlx", "exchange_dlx", "dead.letter");
        
        channel.basicPublish("exchange_normal", "dead.letter.willDie", null, "测试死信队列".getBytes());
        
        
        Thread.sleep(3000);
        
        channel.close();
        connection.close();
        
    }
}
