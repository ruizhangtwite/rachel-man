package com.zhangrui.demo.controller;

import com.zhangrui.demo.service.IConnector;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nsc on 2018/3/23.
 */
@Controller
@RequestMapping("/demo")
public class MyController implements EnvironmentAware{

    @Autowired
    private IConnector connector;

    @GetMapping("/method")
    @ResponseBody
    public String myMethod(){
        return  "你好，我是小张！！！！";
    }


    @GetMapping("/connector")
    @ResponseBody
    public String testConnector(){
        return  connector.getConnector();
    }


    /**
     * 这种方式被证实是不可行的
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        if (environment instanceof ConfigurableEnvironment){
            ConfigurableEnvironment env = ConfigurableEnvironment.class.cast(environment);
            MutablePropertySources mutablePropertySources = env.getPropertySources();

            Map<String, Object> map = new HashMap<>();
            map.put("server.port", 9999);
            MapPropertySource propertySource = new MapPropertySource("custom-env", map);

            mutablePropertySources.addFirst(propertySource);
        }
    }
}
