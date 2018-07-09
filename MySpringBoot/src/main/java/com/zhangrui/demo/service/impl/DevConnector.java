package com.zhangrui.demo.service.impl;

import com.zhangrui.demo.service.IConnector;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Created by nsc on 2018/3/23.
 */
@Service
@Profile("dev")
public class DevConnector implements IConnector {


    @Override
    public String getConnector() {
        return "我是开发版本哦！！";
    }
}
