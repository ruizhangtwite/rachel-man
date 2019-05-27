package com.zhangrui.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public class FirstObserver implements Observer {

    public FirstObserver(Observable observable){
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("得到了通知");
    }
}
