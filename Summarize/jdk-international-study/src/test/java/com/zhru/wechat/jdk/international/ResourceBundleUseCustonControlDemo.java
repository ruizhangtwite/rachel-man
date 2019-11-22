package com.zhru.wechat.jdk.international;


import com.zhru.wechat.jdk.international.reslove.control.CustomControl;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Desp: 使用自定义的{@link java.util.ResourceBundle.Control}测试国际化操作
 * 2019-10-27 14:38
 * Created by zhru.
 */
public class ResourceBundleUseCustonControlDemo {

    public static void main(String[] args) {

        ResourceBundle.Control control = new CustomControl("UTF-8");

        /**
         * 以en_US语言编码进行加载配置
         */
        ResourceBundle bundle = ResourceBundle.getBundle("myconfig", Locale.US, control);
        String address = bundle.getString("address");
        System.out.println("当前的语言编码是：" + Locale.US
                + "，获取的address值：" + address);

        /**
         * 以zh_CN语言编码进行加载配置
         */
        bundle = ResourceBundle.getBundle("myconfig", Locale.SIMPLIFIED_CHINESE, control);
        address = bundle.getString("address");
        System.out.println("当前的语言编码是：" + Locale.SIMPLIFIED_CHINESE
                + "，获取的address值：" + address);
    }
}
