package com.grpc.proxy;

import com.grpc.sock.GrpcClient;
import com.grpc.sock.GrpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Desp:接口动态代理类
 * 2018-08-09 23:23
 * Created by zhangrui.
 */
public class SayHelloServiceProxy implements InvocationHandler{

    private String host;
    private int port;

    public SayHelloServiceProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public <T> T getProxy(Class<T> interfaceClz) {
        return (T)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{interfaceClz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        GrpcRequest request = new GrpcRequest();
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParams(args);
        
        return new GrpcClient(host, port).invoke(request);
    }
}
