package com.zhangrui.design.iterator;

/**
 * Desp:
 * 2019-05-24 20:04
 * Created by zhangrui.
 */
public class Student {
    
    private String name;
    
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age='" + age + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
