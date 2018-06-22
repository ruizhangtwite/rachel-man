package com.zhangrui.test;

import com.zhangrui.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CountDownLatch;

/**
 * Desp:多线程并发测试MyConnecttionPool
 * 2018-05-19 13:48
 * Created by zhangrui.
 */
public class ConnectionPoolTest {

    private static Integer NUM = 1000;

    static CountDownLatch latch = new CountDownLatch(NUM);

    ConnectionPool pool = new ConnectionPool(20, 10);

    public void testConnectionPool() throws Exception{

        Connection connection = pool.getConnection();

        PreparedStatement statement = connection.prepareStatement("select * from user where id = ?");
        statement.setInt(1, 1);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet != null){
            while (resultSet.next()){
                String name = resultSet.getString("name");

                System.out.println(Thread.currentThread().getName() + ": 获取了值--> " + name);
            }
        }

        connection.close();

    }

    public static void main(String[] args) {
        final ConnectionPoolTest test = new ConnectionPoolTest();
        for (int i = 0; i < NUM; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        test.testConnectionPool();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            latch.countDown();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
