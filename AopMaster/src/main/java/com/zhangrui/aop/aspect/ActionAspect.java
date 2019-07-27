package com.zhangrui.aop.aspect;

import com.zhangrui.aop.annotation.Aspect;
import com.zhangrui.aop.annotation.BeforeAdvice;
import com.zhangrui.aop.annotation.Pointcut;

/**
 * Desp:Aspect
 * 2019-07-27 17:44
 * Created by zhangrui.
 */
@Aspect
public class ActionAspect {
    
    @Pointcut(value = ".+ com.zhangrui.aop.mo.+(.+)")
    public void pointcut(){}
    
    
    @BeforeAdvice(value = "pointcut()")
    public void before(){
        System.out.println("Before........");
        System.out.println("我是before的前置AOP");
    }
    
    
    
}
