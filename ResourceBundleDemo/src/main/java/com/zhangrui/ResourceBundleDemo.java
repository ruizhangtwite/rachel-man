package com.zhangrui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Desp:
 * 2018-04-21 14:02
 * Created by zhangrui.
 */
public class ResourceBundleDemo {

    public static void main(String[] args) {
        //原始的ResourceBundle
        ResourceBundle bundle = ResourceBundle.getBundle("static.my");
        String name = bundle.getString("name");
        System.out.println(Locale.getDefault());
        System.out.println("原始的中文會亂碼：" + name);

        //借用PropertyResourceBundle
        InputStream stream = ResourceBundleDemo.class.getClassLoader().getResourceAsStream("static/my_zh_CN.properties");
        try {
            bundle = new PropertyResourceBundle(new InputStreamReader(stream, "GBK"));
            System.out.println("PropertyResourceBundle的中文需要添加编码格式：" + bundle.getString("name"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //借用ResourceBundle.Control
        try {
            bundle = new ResouceBundleControl("GBK").newBundle("static.my", Locale.getDefault(),"java.properties",ResourceBundleDemo.class.getClassLoader(),true);
            System.out.println("ResourceBundle.Control---->" + bundle.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
