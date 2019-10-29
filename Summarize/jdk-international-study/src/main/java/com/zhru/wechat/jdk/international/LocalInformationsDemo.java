package com.zhru.wechat.jdk.international;

import java.util.Locale;
import java.util.stream.Stream;

/**
 * Desp: 获取各个语言及其代码信息
 * 2019-10-25 21:35
 * Created by zhru.
 */
public class LocalInformationsDemo {

    public static void main(String[] args) {
        Stream.of(Locale.getAvailableLocales()).forEach(local -> 
            System.out.println(local.getDisplayName() + ":" + local)
        );
    }
}
