package com.zhangrui.observer;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public class ObservableTest {

    public static void main(String[] args) {

        SomethingObservable observable = new SomethingObservable();
        observable.addObserver(new FirstObserver(observable));
        observable.setData();

        Something something = new Something(observable);
        something.doSomething();
    }
}
