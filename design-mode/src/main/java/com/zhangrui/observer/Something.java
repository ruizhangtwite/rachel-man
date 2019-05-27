package com.zhangrui.observer;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public class Something {

    private SomethingObservable observable;

    public Something(SomethingObservable observable){
        this.observable = observable;
    }

    public void doSomething(){
        System.out.println("Do Something.....");
        this.observable.setChanged();
        this.observable.notifyObservers();
    }
}
