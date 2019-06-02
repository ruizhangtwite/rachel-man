package com.zhangrui.rabbit.alternate;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Desp: 备份交换器，当消息发送到某个交换器的时候，交换器无法找到对应的队列，如果这个交换器有备份交换器的话，
 * 可以发送给备份交换器
 * 2019-06-02 22:46
 * Created by zhangrui.
 */
public class AlternateExchangeProvider {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        /**
         * 定义一个交换器，并设置它的备份交换器，
         * 在args参数中指定key:alternate-exchage,value：备份交换器的名称
         */
        Map<String, Object> map = new HashMap<>();
        map.put("alternate-exchange", "alternate_exchange");
        channel.exchangeDeclare("normal_exchange", "direct", false, false, map);
        
        channel.queueDeclare("normal_queue", false, false, false, null);
        channel.queueBind("normal_queue", "normal_exchange", "alternate_exchange");
        
        channel.exchangeDeclare("alternate_exchange", "fanout", false, false, null);
        channel.queueDeclare("alternate_queue", false, false, false, null);
        /**
         * 因为是fanout类型的交换器，因此只要跟它进行了绑定的queue都可以收到消息
         */
        channel.queueBind("alternate_queue", "alternate_exchange", "");
        /**
         * 通过更改对应的routing_key，来测试
         */
        channel.basicPublish("normal_exchange", "alternate_exchange_not", null, "测试anternate_queue".getBytes());
        
        Thread.sleep(4000);
        
        
        channel.close();
        connection.close();
        


    }

}
