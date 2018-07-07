package com.zhangrui.tomcat;

/**
 * Desp:Server服务类
 * 2018-05-19 22:44
 * Created by zhangrui.
 */
public class StandServer {
    public void start() throws Exception{
        new StandService().start();
    }
}
