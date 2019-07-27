package com.zhangrui.aop.mo;

/**
 * Desp: HelloService
 * 2019-07-27 17:40
 * Created by zhangrui.
 */
public interface HelloService {
    
    void sayHello(String name);
    
    void aroundHello(String name);
}
