package com.zhangrui.protobuf.my;

import java.util.Arrays;

/**
 * Desp: 模拟Protobuf的运算模式
 * 2019-03-24 12:42
 * Created by zhangrui.
 */
public class SimulateProtobuf {

    /**
     * protoBuf的的运算模式是将大的字节进行缩减，减少数值所占用的字节数，而且是低位->高位的形式
     */

    public static void main(String[] args) {
        int num = 900;
        byte[] protoBufByte = new byte[4];  //int类型是个字节，因此，长度是4位
        /**
         * 将num依次与0xFF000000,0x00FF0000,0x0000FF00,0x000000FF作位运算,然后右移到低位
         */

        /**
         * protobuf的原理也是高位右移到低位，但是是每次只取后7位，如果后面还有值得话则第8位设为1，没有则为
         * 0，依靠这个值来判断是否后面的值算在这个值里面
         */
        protoBufByte[3] = (byte) ((num & 0xFF000000) >> (3 * 8));
        protoBufByte[2] = (byte) ((num & 0x00FF0000) >> (2 * 8));
        protoBufByte[1] = (byte) ((num & 0x0000FF00) >> (1 * 8));
        protoBufByte[0] = (byte) (num & 0x000000FF);

        System.out.println(Arrays.toString(protoBufByte));
        
        
    }
}
