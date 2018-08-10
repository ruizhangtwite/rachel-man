package com.grpc;

import com.grpc.service.SayHelloService;
import com.grpc.service.impl.SayHelloServiceImpl;
import com.grpc.sock.GrpcServer;

/**
 * Desp:
 * 2018-08-10 1:27
 * Created by zhangrui.
 */
public class GrpcServerMain {
    public static void main(String[] args) {
        SayHelloService service = new SayHelloServiceImpl();
        GrpcServer server = new GrpcServer(service, 9999);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
