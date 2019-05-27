package com.zhangrui.deadlock.reentrant;

import java.util.Random;

/**
 * 借助synchronized实现的账户转移, 这种情况会导致死锁
 * 虽然是按照固定的顺序去加锁
 * @Author zr
 * @Date 2019-05-27
 **/
public class LivingTransderMoney implements TransferMoney {

    @Override
    public void transfer(User from, User to) {
        for(;;) {

            if (from.getLock().tryLock()) {
                try {
                    System.out.println(from.getName() + " begin...");

                    if (to.getLock().tryLock()){
                        try {

                            to.transfer(from);
                            System.out.println(to.getName() + " transfer....");
                            System.out.println(to.getName() + "的Money是" + to.getMoney());

                            break;
                        } finally {
                            to.getLock().unlock();
                        }
                    }
                } finally {
                    from.getLock().unlock();
                }
            }

            try {
                Thread.sleep(new Random().nextInt(20));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
