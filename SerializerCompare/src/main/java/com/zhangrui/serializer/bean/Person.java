package com.zhangrui.serializer.bean;

import java.io.Serializable;

/**
 * @Author zr
 * @Date 19-4-17
 **/
public class Person implements Serializable {

    private Integer id;
    private String name;

    public Person() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
