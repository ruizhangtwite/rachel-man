package com.zhangrui.platform.context;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * Created by nsc on 2018/4/3.
 */
public class SpringServletAware implements ServletContextAware {

    private static ServletContext servletContext;
    @Override
    public void setServletContext(ServletContext servletContext) {
        SpringServletAware.servletContext = servletContext;
    }

    public static ServletContext getServletContext(){
        return servletContext;
    }
}
