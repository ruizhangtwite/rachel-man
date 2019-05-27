package com.zhangrui.design.observer;

/**
 * Desp:
 * 2019-05-24 19:34
 * Created by zhangrui.
 */
public interface EventListener {
    
    public void execute(EventObj eventObj);
    
    public String getExecuteObjName();
}
