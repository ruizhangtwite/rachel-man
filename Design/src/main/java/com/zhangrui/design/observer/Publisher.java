package com.zhangrui.design.observer;

import java.util.HashMap;
import java.util.Map;

/**
 * Desp:
 * 2019-05-24 19:34
 * Created by zhangrui.
 */
public class Publisher {
    
    Map<String, EventListener> eventListenerMap = new HashMap<>();
    
    public void register(EventListener eventListener){
        eventListenerMap.put(eventListener.getExecuteObjName(), eventListener);
    }
    
    
    public void publishEvent(EventObj eventObj){
        EventListener eventListener = eventListenerMap.get(eventObj.getClass().getName());
        eventListener.execute(eventObj);
    }
    
}
