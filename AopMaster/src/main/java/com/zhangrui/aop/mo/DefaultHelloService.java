package com.zhangrui.aop.mo;

/**
 * Desp:
 * 2019-07-27 17:42
 * Created by zhangrui.
 */
public class DefaultHelloService implements HelloService {
    
    @Override
    public void sayHello(String name) {
        System.out.println(this.getClass().getName() + " say " + name);
    }

    @Override
    public void aroundHello(String name) {
        System.out.println(this.getClass().getName() + " around " + name);
    }
}
