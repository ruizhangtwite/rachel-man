package com.zhangrui.lock;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.*;

/**
 * 定义网络传输中的数据序列化过程
 */
public class DistributeSerializer implements ZkSerializer{
    @Override
    public byte[] serialize(Object data) throws ZkMarshallingError {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ObjectOutputStream objectInputStream = new ObjectOutputStream(outStream);
            objectInputStream.writeObject(data);
            return outStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
