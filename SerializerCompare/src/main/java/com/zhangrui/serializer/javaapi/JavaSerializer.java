package com.zhangrui.serializer.javaapi;

import com.zhangrui.serializer.bean.Person;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * @Author zr
 * @Date 2019-4-17
 **/
public class JavaSerializer {

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);


        System.out.println("Java序列化整型数据***************************");
        Person person = new Person();
        person.setId(296);
        objectOutputStream.writeObject(person);
        System.out.println(Arrays.toString(arrayOutputStream.toByteArray()));


        System.out.println("Java序列化字符型数据***************************");
        person = new Person();
        person.setName("zhangrui");
        objectOutputStream.writeObject(person);
        System.out.println(Arrays.toString(arrayOutputStream.toByteArray()));



        System.out.println("Java序列化数据***************************");
        person = new Person();
        person.setId(300);
        person.setName("wps");
        objectOutputStream.writeObject(person);
        System.out.println(Arrays.toString(arrayOutputStream.toByteArray()));

    }
}
