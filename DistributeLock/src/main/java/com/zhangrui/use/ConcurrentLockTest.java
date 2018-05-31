package com.zhangrui.use;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟并发操作，借助Lock
 */
public class ConcurrentLockTest {

    ReentrantLock lock = new ReentrantLock();

    private static CountDownLatch latch = new CountDownLatch(20);

    private Integer num = 1;

    private void generateNum(){
        try{
            lock.lock();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String s = format.format(date);
            System.out.println(s + "-" + num++);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ConcurrentLockTest test = new ConcurrentLockTest();
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
