package com.zhangrui.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 优化版的分布式锁，原理见有道云笔记-分布式锁的设计
 */
public class DistributeImproveLock implements Lock {

    private ZkClient zkClient;
    private final String ROOT_LOCK = "/ROOT_LOCKS";
    private String lockName;

    ThreadLocal<String> PRE_NODE = new ThreadLocal<>();
    ThreadLocal<String> CURRENT_NODE = new ThreadLocal<>();

    public DistributeImproveLock(String config, String lockName) {
        if (!lockName.startsWith("/")){
            lockName = "/" + lockName;
        }
        this.lockName = lockName;
        this.zkClient = new ZkClient(config);
        this.zkClient.setZkSerializer(new DistributeSerializer());
        if (!zkClient.exists(ROOT_LOCK)){
            try {
                zkClient.createPersistent(ROOT_LOCK);
            } catch (ZkNodeExistsException e){

            }
        }
    }

    @Override
    public void lock() {
        if (!tryLock()){
            waitForLock();
            this.lock();    //这行加上会出现问题
        }

        System.out.println(Thread.currentThread().getName() +  "获得了锁");
    }

    @Override
    public boolean tryLock() {
        try {
            String splitStr = "_lock_";
            if (lockName.contains(splitStr)) {
                throw new Exception("your lockName includes + \'" + splitStr + "\'" );
            }
			if (CURRENT_NODE.get() == null)
				CURRENT_NODE.set(this.zkClient.createEphemeralSequential(ROOT_LOCK + lockName + splitStr, new byte[0]));
			}
            // 取出所有lockName的锁，getChildren()方法获取的路径不以ROOT_LOCK开头，只有ROOT_LOCK后面的部分
            List<String> children = this.zkClient.getChildren(ROOT_LOCK);
            List<String> nodeLists = new ArrayList<>();
            for (String child : children){
                String nodePathPrefix = "/" + child.split(splitStr)[0] ;
                if (nodePathPrefix.equals(lockName)){
                    nodeLists.add(child);
                }
            }

            Collections.sort(nodeLists);
            // 若当前节点为最小节点，则获取锁成功
            if (CURRENT_NODE.get().equals(ROOT_LOCK + "/" + nodeLists.get(0))){
                return true;
            } else {
                // 获取锁失败，则获取它的前节点
                int index = nodeLists.indexOf(CURRENT_NODE.get().substring(CURRENT_NODE.get().lastIndexOf("/") + 1));
                PRE_NODE.set(ROOT_LOCK + "/" + nodeLists.get(index - 1));
            }

        } catch (Exception e){
            return false;
        }

        return false;
    }

    private void waitForLock() {
        final CountDownLatch latch = new CountDownLatch(1);

        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                latch.countDown();
                System.out.println("-------监听到节点被删除");
            }
        };

        this.zkClient.subscribeDataChanges(PRE_NODE.get(), listener);

        // 阻塞自己
        if (this.zkClient.exists(PRE_NODE.get())){
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.zkClient.unsubscribeDataChanges(PRE_NODE.get(), listener);
    }

    @Override
    public void unlock() {
        if (this.zkClient.exists(CURRENT_NODE.get())){
            this.zkClient.delete(CURRENT_NODE.get());
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
