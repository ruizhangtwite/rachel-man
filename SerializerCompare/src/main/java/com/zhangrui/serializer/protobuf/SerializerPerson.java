package com.zhangrui.serializer.protobuf;

import java.util.Arrays;

/**
 * @Author zr
 * @Date 2019-4-17
 **/
public class SerializerPerson {

    public static void main(String[] args) throws Exception{

        System.out.println("Protobuf序列化整型数据***************************");
        PersonMan.Person personId = PersonMan.Person.newBuilder().setId(296).build();
        System.out.println(Arrays.toString(personId.toByteArray()));


        System.out.println("Protobuf序列化字符型数据***************************");
        PersonMan.Person personName = PersonMan.Person.newBuilder().setName("zhangrui").build();
        System.out.println(Arrays.toString(personName.toByteArray()));



        System.out.println("Protobuf序列化数据***************************");
        PersonMan.Person person = PersonMan.Person.newBuilder().setId(300).setName("wps").build();
        System.out.println(Arrays.toString(person.toByteArray()));


    }
}
