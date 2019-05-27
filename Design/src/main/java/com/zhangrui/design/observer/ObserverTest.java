package com.zhangrui.design.observer;

/**
 * Desp:
 * 2019-05-24 19:51
 * Created by zhangrui.
 */
public class ObserverTest {

    public static void main(String[] args) {
        EventObj eventObj = new CookEventObj();
        Publisher publisher = new Publisher();
        
        publisher.register(new CookEventListener(eventObj));
        
        publisher.publishEvent(eventObj);
    }
}
