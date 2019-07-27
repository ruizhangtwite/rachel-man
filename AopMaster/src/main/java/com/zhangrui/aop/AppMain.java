package com.zhangrui.aop;

import com.zhangrui.aop.loader.AopLoader;
import com.zhangrui.aop.mo.HelloService;

/**
 * Desp:
 * 2019-07-28 6:01
 * Created by zhangrui.
 */
public class AppMain {

    public static void main(String[] args) {
        AopLoader loader = new AopLoader();
        String file = Thread.currentThread().getClass().getResource("/").getFile();
        loader.loadAspect(file);

        HelloService helloService = (HelloService) AopLoader.getProxy("DefaultHelloService");
        helloService.sayHello("你好啊");
    }
}
