package com.zhangrui.design.observer;

/**
 * Desp:
 * 2019-05-24 19:41
 * Created by zhangrui.
 */
public class CookEventObj implements EventObj {
    @Override
    public String getType() {
        return "cook";
    }

    @Override
    public void doSomthing() {
        System.out.println("我是Cooker");
    }
}
