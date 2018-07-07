package com.zhangrui.tomcat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Desp:处理请求
 * 2018-05-19 22:45
 * Created by zhangrui.
 */
public class StandConnector {

    private Integer port;
    private ServerSocket serverSocket = null;

    // 处理请求的线程池
    public static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(25, 50, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    public StandConnector(Integer port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("start tomcat and port:9999");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void start() throws Exception{

        while (true){
            final Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        new StandSocketAdaptor().service(socket);
                    } catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }
}
