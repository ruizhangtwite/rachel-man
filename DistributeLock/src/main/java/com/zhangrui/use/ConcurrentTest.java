package com.zhangrui.use;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟并发操作,会发现出现了问题，并不是按照预计的结果进行展示
 */
public class ConcurrentTest {

    private static CountDownLatch latch = new CountDownLatch(20);

    private Integer num = 1;

    private void generateNum(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s = format.format(date);
        System.out.println(s + "-" + num++);
    }

    public static void main(String[] args) {
        final ConcurrentTest test = new ConcurrentTest();
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

        /*try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
    }
}
