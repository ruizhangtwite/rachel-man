package com.zhangrui.use;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟并发操作，借助Synchronized
 */
public class ConcurrentSynchronizedTest {

    private static CountDownLatch latch = new CountDownLatch(20);

    private Integer num = 1;

    private synchronized void generateNum(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s = format.format(date);
        System.out.println(s + "-" + num++);
    }

    public static void main(String[] args) {
        final ConcurrentSynchronizedTest test = new ConcurrentSynchronizedTest();
        for (int i = 0; i < 20; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        test.generateNum();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            latch.countDown();
        }

    }
}
