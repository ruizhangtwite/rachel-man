package com.zhangrui.tomcat;

/**
 * Desp:启动类
 * 2018-05-19 22:41
 * Created by zhangrui.
 */
public class BootStrap {

    public static void main(String[] args) {
        try {
            new Catalina().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
