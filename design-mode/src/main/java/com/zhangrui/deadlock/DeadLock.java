package com.zhangrui.deadlock;

/**
 * @Author zr
 * @Date 2019-05-23
 **/
public class DeadLock {

    private Object object1 = new Object();

    private Object object2 = new Object();

    public void test1(){
        synchronized (object1){
            System.out.println(Thread.currentThread().getName() + "获取了object1的锁");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object2){
                System.out.println(Thread.currentThread().getName() + "获取了object2的锁");
            }
        }
    }

    public void test2(){
        synchronized (object2){
            System.out.println(Thread.currentThread().getName() + "获取了object2的锁");

            synchronized (object1){
                System.out.println(Thread.currentThread().getName() + "获取了object1的锁");

            }
        }
    }

    public static void main(String[] args) {
        DeadLock lock = new DeadLock();

        new Thread(() -> {
            lock.test1();
        }).start();

        new Thread(() -> {
            lock.test2();
        }).start();

    }
}
