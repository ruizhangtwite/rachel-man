package com.zhangrui.springbootthinking.springautoconfiguration.canscan;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Desp:
 * 2019-05-28 22:06
 * Created by zhangrui.
 */
@Component
public class CanScanComponent {
    @Bean
    public User user(){
        return new User();
    }
    
}
