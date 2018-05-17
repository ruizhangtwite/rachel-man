package com.zhangrui.platform.rediscache;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * Created by nsc on 2018/4/3.
 */
public class RedisKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object o, Method method, Object... objects) {
        StringBuilder sb = new StringBuilder();
        sb.append(o.getClass().getName()).append(".");
        sb.append(method.getName()).append(".");
        for (Object obj : objects) {
            sb.append(obj.toString());
        }
        return sb.toString();
    }
}
