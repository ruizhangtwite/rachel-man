package com.grpc.zr;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Desp:
 * 2018-08-09 22:03
 * Created by zhangrui.
 */
public class MyServer {
    
    private Server server;
    private  int port = 8888;

    public static void main(String[] args) throws Exception{
        MyServer myServer = new MyServer();
        myServer.start();
        myServer.await();
    }

    public  void start() throws IOException{
        server = ServerBuilder.forPort(port).addService(new GrpcServiceImpl()).build().start();
        System.out.println("Server is running...");
        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
                System.out.println("Server is shutdown...");
                MyServer.this.stop();
            }
        ));
    }
    
    public void stop(){
        if (server != null){
            server.shutdown();
        }
    }
    
    public void await() throws InterruptedException {
        if (server != null){
            server.awaitTermination();
        }
    }
}
