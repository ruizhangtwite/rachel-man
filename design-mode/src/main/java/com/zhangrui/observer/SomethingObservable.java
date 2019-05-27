package com.zhangrui.observer;

import java.util.Observable;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public class SomethingObservable extends Observable {

    @Override
    public void setChanged() {
        super.setChanged();
    }

    public void setData(){
        setChanged();
        notifyObservers();
    }
}
