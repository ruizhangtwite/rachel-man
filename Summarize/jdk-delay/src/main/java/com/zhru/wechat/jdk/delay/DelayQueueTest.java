package com.zhru.wechat.jdk.delay;

import com.zhru.wechat.jdk.delay.bean.DelayItem;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Desp:
 * 2019-11-12 23:25
 * Created by zhru.
 */
public class DelayQueueTest {

    private static final DelayQueue<DelayItem> DELAY_ITEM_DELAY_QUEUE =
            new DelayQueue<>();

    private static final ExecutorService EXE = Executors.newFixedThreadPool(2);

    private void produce() {
        EXE.execute(this::produceItem);
    }

    private void produceItem() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            long v = random.nextInt(200);
            DelayItem item = new DelayItem(v);
            DELAY_ITEM_DELAY_QUEUE.add(item);
        }
        
    }

    private void consume() {
        EXE.execute(this::consumeItem);
    }

    private void consumeItem() {
        while (true) {
            System.out.println("获取的DelayItem：");
            DelayItem delayItem = null;
            try {
                delayItem = DELAY_ITEM_DELAY_QUEUE.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (delayItem == null) {
                break;
            }
            System.out.println(delayItem);
        }

    }


    public static void main(String[] args) throws Exception {
        
        DelayQueueTest delayQueueTest = new DelayQueueTest();
        
        delayQueueTest.produce();

        Thread.sleep(TimeUnit.SECONDS.toMillis(5));

        delayQueueTest.consume();

        EXE.shutdown();
    }
}
