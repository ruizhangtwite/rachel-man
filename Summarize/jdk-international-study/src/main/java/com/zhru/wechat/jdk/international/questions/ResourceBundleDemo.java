package com.zhru.wechat.jdk.international.questions;

import java.util.*;
import java.util.spi.ResourceBundleControlProvider;

/**
 * Desp: {@link java.util.ResourceBundle}加载配置信息
 * 2019-10-25 21:43
 * Created by zhru.
 */
public class ResourceBundleDemo {
    
    static {
        List<ResourceBundleControlProvider> list = null;
        ServiceLoader<ResourceBundleControlProvider> serviceLoaders
                = ServiceLoader.loadInstalled(ResourceBundleControlProvider.class);

        System.out.println(serviceLoaders.iterator().hasNext());
        for (ResourceBundleControlProvider provider : serviceLoaders) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(provider);
        }
    }

    public static void main(String[] args) {

        
        /**
         * 以en_US语言编码进行加载配置
         */
        ResourceBundle bundle = ResourceBundle.getBundle("myconfig", Locale.US);
        String address = bundle.getString("address");
        System.out.println("当前的语言编码是：" + Locale.US
                + "，获取的address值：" + address);

        /**
         * 以zh_CN语言编码进行加载配置
         */
        bundle = ResourceBundle.getBundle("myconfig", Locale.SIMPLIFIED_CHINESE);
        address = bundle.getString("address");
        System.out.println("当前的语言编码是：" + Locale.SIMPLIFIED_CHINESE
                + "，获取的address值：" + address);
    }

}
