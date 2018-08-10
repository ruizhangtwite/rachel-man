package com.grpc.service.impl;

import com.grpc.service.SayHelloService;

/**
 * Desp:服务实现类
 * 2018-08-09 23:17
 * Created by zhangrui.
 */
public class SayHelloServiceImpl implements SayHelloService {
    @Override
    public void say(String name) {
        System.out.println("我是来自于服务器的>>" + name);
    }
}
