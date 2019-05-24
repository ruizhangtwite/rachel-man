package com.zhangrui.design.observer;

/**
 * Desp:
 * 2019-05-24 19:41
 * Created by zhangrui.
 */
public class CookEventListener implements EventListener {
    
    private EventObj eventObj;
    
    public CookEventListener(EventObj eventObj){
        this.eventObj = eventObj;
    }
    
    @Override
    public void execute(EventObj eventObj) {
        System.out.println("我在执行" + eventObj.getType() + "的事件");
    }

    @Override
    public String getExecuteObjName() {
        return this.eventObj.getClass().getName();
    }
}
