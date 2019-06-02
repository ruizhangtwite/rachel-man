package com.zhangrui.deadlock.reentrant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author zr
 * @Date 2019-05-27
 **/
public class User {

    private ReentrantLock lock;

    private String name;
    private Integer money;

    public User(String name, Integer money) {
        this.name = name;
        this.money = money;

        this.lock = new ReentrantLock();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lock getLock(){
        return this.lock;
    }

    public void transfer(User user){
        this.money += user.getMoney();
    }
}
