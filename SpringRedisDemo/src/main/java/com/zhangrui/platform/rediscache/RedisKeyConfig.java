package com.zhangrui.platform.rediscache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

/**
 * Created by nsc on 2018/4/3.
 */
//@Configuration
//@EnableCaching
public class RedisKeyConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisKeyGenerator redisKeyGenerator;

    @Override
    public KeyGenerator keyGenerator() {
        return this.redisKeyGenerator;
    }
}
