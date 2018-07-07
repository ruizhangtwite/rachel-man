package com.zhangrui.tomcat;

/**
 * Desp:两个作用 1、Engine加载Servlet 2、Connector运行Servlet
 * 2018-05-19 22:45
 * Created by zhangrui.
 */
public class StandService {
    public void start() throws Exception{
        //加载web.xml文件，加载Servlet
        new StandEngine().start();

        //Conector用于接收网络请求,运行Servlet
        new StandConnector(9999).start();
    }
}
