package com.zhru.wechat.jdk.delay.bean;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Desp:延迟实体类
 * 2019-11-12 23:15
 * Created by zhru.
 */
public class DelayItem implements Delayed {

    private long time;
    private long stayTime;

    public DelayItem(long stayTime) {
        this.stayTime = stayTime;
        this.time = System.nanoTime()
                + TimeUnit.SECONDS.toNanos(stayTime);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.toNanos(time - System.nanoTime());
    }

    @Override
    public int compareTo(Delayed other) {
        long diff = getDelay(NANOSECONDS) - other.getDelay(NANOSECONDS);
        return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
    }

    @Override
    public String toString() {
        return "DelayItem{" +
                "stayTime=" + stayTime +
                '}';
    }
}
