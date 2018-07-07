package com.zhangrui.tomcat;

/**
 * Desp:只定义一个Host的Engine对象
 * 2018-05-19 22:45
 * Created by zhangrui.
 */
public class StandEngine {

    public static StandHost standHost = null;

    public void start() throws Exception{
        standHost = new StandHost("localhost", "E:\\tomcat");
        standHost.start();
    }
}
