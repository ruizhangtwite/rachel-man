package com.zhru.wechat.jdk.compile.processor.bean;

/**
 * @Author zhru
 * @Date 2019-11-04
 **/
public class Person {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
