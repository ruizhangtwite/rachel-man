package com.zhangrui.springbootthinking.springautoconfiguration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * Desp:
 * 2019-05-28 22:07
 * Created by zhangrui.
 */
public class ComponentDefaultScan {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.register(MainConfiguration.class);
        configApplicationContext.refresh();

        System.out.println("register bean .....");
        String[] names = configApplicationContext.getBeanDefinitionNames();
        Stream.of(names).forEach(System.out::println);
        
        configApplicationContext.close();
    }
}
