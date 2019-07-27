package com.zhangrui.aop.ref;

import com.zhangrui.aop.MethodRequest;
import com.zhangrui.aop.loader.AopLoader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;

/**
 * Desp:
 * 2019-07-27 17:51
 * Created by zhangrui.
 */
public class AopInvocation implements InvocationHandler {
    
    private Object object;
    
    public AopInvocation(){
        
    }
    
    public AopInvocation(Object o){
        this.object = o;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String s = method.toString();
        boolean match = AopLoader.isMatch(s);
        if (match) {
            LinkedList<MethodRequest> methodRequests = AopLoader.getMethod(s);
            
            if (methodRequests.size() > 0){
                for (MethodRequest request : methodRequests) {
                    Method requestMethod = request.getMethod();
                    Object o = requestMethod.getDeclaringClass().newInstance();
                    requestMethod.invoke(o);
                }
            }
        }

        method.invoke(object, args);


        return null;
    }
    
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }
    
}
