package com.zhangrui.platform.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by nsc on 2018/4/3.
 */
public class SpringMvcAware implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringMvcAware.applicationContext = applicationContext;

    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
