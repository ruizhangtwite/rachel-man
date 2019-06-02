package com.zhangrui.rabbit.mandatory;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Desp: Mandatory为true的时候，表示当交换器无法找到匹配的队列用于存放消息的时候，会直接将消息
 * 返回给生产者，但是这个通知需要手动进行捕获，借助returnListener
 * 2019-06-02 22:27
 * Created by zhangrui.
 */
public class MandatoryProvider {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        
        
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, 
                                     String exchange, String routingKey, 
                                     AMQP.BasicProperties properties, 
                                     byte[] body) throws IOException {

                System.out.println("replyCode:" + replyCode + ",replyText:" + replyText
                + ",exchange:" + exchange + ",routingKey:" + routingKey + ",properties:" + properties
                + ",body:" + new String(body));
                
            }
        });
        
        channel.exchangeDeclare("mandatory_exchange", "direct", true, false, null);
        channel.queueDeclare("mandatory_queue", true, false, false, null);
        channel.queueBind("mandatory_queue", "mandatory_exchange",  "mandatory_key");
        /**
         * mandatory:
         * 1.false : 代表当exchange无法找到合适的queue的时候，直接丢弃
         * 2.true : 当exchange无法找到合适的queue的时候，将消息返回给生产者
         * 更改routing_key测试效果
         */
        channel.basicPublish("mandatory_exchange", "mandatory_key_not_find_queue", true, null, "我是用来测试Mandatory的".getBytes());
        
        
        Thread.sleep(3000);
        
        connection.close();


    }
}
