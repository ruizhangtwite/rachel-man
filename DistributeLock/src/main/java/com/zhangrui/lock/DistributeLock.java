package com.zhangrui.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 借助Zookeeper,自定义分布式锁
 * 1、在zookeeper中在同一个节点下是不能有重复的节点路径
 * 2、借助不能重复这个点，实现分布式锁的设计
 * 不足：当节点删除时，所有的线程都会去争锁，有惊群效应
 */
public class DistributeLock implements Lock {

    private ZkClient zkClient;
    private String lockPath;

    public DistributeLock(String config, String lockPath) {
        this.lockPath = lockPath;
        this.zkClient = new ZkClient(config);
        this.zkClient.setZkSerializer(new DistributeSerializer());
    }

    @Override
    public boolean tryLock() {
        try {
            this.zkClient.createPersistent(this.lockPath);
        } catch (ZkNodeExistsException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (!tryLock()){
            waitForLock(time, unit);
        }
        return false;
    }

    @Override
    public void unlock() {
        if (this.zkClient.exists(this.lockPath)){
            this.zkClient.delete(this.lockPath);
        }
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            waitForLock();
            //lock();    //最开始多了这一句，几个小时没有成功
        }
        System.out.println(Thread.currentThread().getName() +  "获得了锁");
    }

    private void waitForLock(long time, TimeUnit unit){
        final CountDownLatch latch = new CountDownLatch(1);

        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("数据发生了改变");
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("数据删除了！！");
                if (latch != null){
                    latch.countDown();
                }
            }
        };

        this.zkClient.subscribeDataChanges(this.lockPath, listener);

        if (this.zkClient.exists(this.lockPath)){
            try {
                latch.await(time, unit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.zkClient.unsubscribeDataChanges(this.lockPath, listener);
    }

    private void waitForLock() {
        final CountDownLatch latch = new CountDownLatch(1);

        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("数据发生了改变");
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("数据删除了！！");
                if (latch != null){
                    latch.countDown();
                }
            }
        };

        this.zkClient.subscribeDataChanges(this.lockPath, listener);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.zkClient.unsubscribeDataChanges(this.lockPath, listener);
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
