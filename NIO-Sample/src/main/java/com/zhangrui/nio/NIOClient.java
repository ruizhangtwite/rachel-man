package com.zhangrui.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * NIO-Client
 * Created by nsc on 2018/4/28.
 */
public class NIOClient {

    private Selector selector;

    public void initConnect(String hostName, int port) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            this.selector = Selector.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(hostName, port));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        while (true) {

            try {
                int i = this.selector.select();
                if (i <= 0) continue;
                Set<SelectionKey> keySet = this.selector.selectedKeys();
                Iterator<SelectionKey> iterator = keySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isConnectable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        // 如果正在连接，则完成连接
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();
                        }
                        // 设置成非阻塞
                        channel.configureBlocking(false);

                        //在这里可以给服务端发送信息哦
                        channel.write(ByteBuffer.wrap(new String("向服务端发送了一条信息").getBytes()));

                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        read(channel);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void read(SocketChannel channel) {

        if (channel == null) {
            return;
        }

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            TimeUnit.SECONDS.sleep(2L);
            channel.read(buffer);
            System.out.println("客户端全部读取完毕");
            
            buffer.flip(); //写模式转为读模式
            
            byte[] array = buffer.array();
            System.out.println("客户端端接收到的数据：" + new String(array, "UTF-8"));
            buffer.clear();

            
            buffer = ByteBuffer.wrap("我是客户端，接收的数据传给你".getBytes());
            channel.write(buffer);
            buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NIOClient client = new NIOClient();
        client.initConnect("localhost", 8888);
        client.listen();
    }
}
