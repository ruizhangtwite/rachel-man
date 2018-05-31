package com.zhangrui.redis;

import redis.clients.jedis.Jedis;

/**
 * 用于基于Jedis了解Redis得通讯协议
 * Created by nsc on 2018/5/23.
 */
public class JedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 9999);

        jedis.set("name".getBytes(), "zr".getBytes());
        jedis.close();
    }
}
