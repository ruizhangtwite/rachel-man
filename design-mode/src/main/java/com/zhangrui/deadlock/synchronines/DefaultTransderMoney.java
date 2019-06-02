package com.zhangrui.deadlock.synchronines;

import java.util.Random;

/**
 * 借助synchronized实现的账户转移, 这种情况会导致死锁
 * 虽然是按照固定的顺序去加锁
 * @Author zr
 * @Date 2019-05-27
 **/
public class DefaultTransderMoney implements TransferMoney {

    @Override
    public void transfer(User from, User to) {
        synchronized (from){
            System.out.println(from.getName() + " begin...");
            try {
                Thread.sleep(new Random().nextInt(30));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (to){
                to.transfer(from);
                System.out.println(to.getName() + " transfer....");
                System.out.println(to.getName() + "的Money是" + to.getMoney());
            }
        }
    }
}
