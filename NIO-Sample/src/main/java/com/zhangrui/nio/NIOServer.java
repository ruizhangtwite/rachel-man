package com.zhangrui.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * NIO-Server
 * Created by nsc on 2018/4/28.
 */
public class NIOServer {

    private Selector selector;
    private Integer port;

    public NIOServer(Integer port) {
        this.port = port;
    }

    public void connect(){

        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        while (true){
            try {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()){
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        byteBuffer.put("我是张瑞".getBytes());
                        byteBuffer.flip();
                        accept.write(byteBuffer);
                        byteBuffer.clear();
                        accept.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()){
                        SocketChannel channel = (SocketChannel) key.channel();
                        read(channel);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void read(SocketChannel channel){
        if (channel == null){
            return;
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            TimeUnit.SECONDS.sleep(2L);
            channel.read(buffer);
            System.out.println("服务器全部读取完毕");
            byte[] array = buffer.array();
            System.out.println("服务器端接收到的数据：" + new String(array, "UTF-8"));


            buffer.flip(); //读模式转为写模式
            buffer = ByteBuffer.wrap("我是服务器，接收的数据传给你".getBytes());
            channel.write(buffer);
            buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NIOServer server = new NIOServer(8888);
        server.connect();
        server.listen();
    }
}
