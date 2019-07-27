package com.zhangrui.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Desp:
 * 2019-07-27 18:55
 * Created by zhangrui.
 */
public class MethodRequest {
    
    private Method method;
    
    private Type[] args;

    public Type[] getArgs() {
        return args;
    }

    public void setArgs(Type[] args) {
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
