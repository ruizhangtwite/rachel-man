package com.zhangrui.demo.context;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

/**
 * 修正Spring Boot的环境配置信息
 *
 * @author nsc
 * @date 2018/7/9
 */
public class CustomEnvironmentApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        ConfigurableEnvironment env = event.getEnvironment();
        MutablePropertySources mutablePropertySources = env.getPropertySources();

        Map<String, Object> map = new HashMap<>();
        map.put("server.port", 9999);
        MapPropertySource propertySource = new MapPropertySource("custom-env", map);

        mutablePropertySources.addFirst(propertySource);

    }
}
