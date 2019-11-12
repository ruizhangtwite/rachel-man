package com.zhru.jdk.beaninfo.bean;

/**
 * 实体类
 * @Author zr
 * @Date 2019-10-24
 **/
public class User {

    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
