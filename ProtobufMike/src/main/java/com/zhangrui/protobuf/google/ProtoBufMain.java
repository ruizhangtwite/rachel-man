package com.zhangrui.protobuf.google;

import com.zhangrui.protobuf.AddressBooks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Desp: 模拟protobuf序列化
 * 2019-03-24 13:13
 * Created by zhangrui.
 */
public class ProtoBufMain {

    public static void main(String[] args) throws IOException {
        AddressBooks.Person person = AddressBooks.Person.newBuilder()
                .setName("zhang").setId(1).setEmail("1538032289@qq.com")
                .addPhones(
                        AddressBooks.Person.PhoneNumber.newBuilder()
                                .setNumber("180612")
                                .setType(AddressBooks.Person.PhoneType.MOBILE)
                                .build())
                .build();

        //序列化
        System.out.println(Arrays.toString(person.toByteArray()));
        //反序列化
        AddressBooks.Person parseFrom = AddressBooks.Person.parseFrom(person.toByteArray());
        System.out.println(parseFrom.getName());
        
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        person.writeTo(outputStream);
        System.out.println(Arrays.toString(outputStream.toByteArray()));
        
        String str = "testing";
        byte[] bytes = str.getBytes(Charset.forName("utf-8"));
        System.out.println(Arrays.toString(bytes));


    }
}
