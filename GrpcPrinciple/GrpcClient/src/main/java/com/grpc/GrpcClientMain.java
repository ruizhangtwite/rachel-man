package com.grpc;

import com.grpc.proxy.SayHelloServiceProxy;
import com.grpc.service.SayHelloService;

/**
 * Desp:
 * 2018-08-10 0:54
 * Created by zhangrui.
 */
public class GrpcClientMain {

    public static void main(String[] args) {
        SayHelloService service = new SayHelloServiceProxy("localhost",9999).getProxy(SayHelloService.class);
        service.say("张瑞");
    }
}
