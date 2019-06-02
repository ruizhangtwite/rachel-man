package com.zhangrui.springbootthinking.springautoconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Desp:
 * 2019-05-28 22:04
 * Created by zhangrui.
 */
@ComponentScan
public class MainConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
