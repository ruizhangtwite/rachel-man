package com.zhangrui.redis;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 模拟Redis服务器
 * 目的是了解RESP协议，为Redis哨兵的编写提供参考依据
 * @author zr
 * @date 2018-05-25
 * @version 1.0
 */
public class RedisServer {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(9999);

        while (true){
            Socket socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            byte[] r = new byte[1024];
            in.read(r);
            System.out.println(new String(r));

            String reply = "+OK\r\n";
            out.write(reply.getBytes());



            in.close();
            out.close();
            socket.close();
        }
    }
}
