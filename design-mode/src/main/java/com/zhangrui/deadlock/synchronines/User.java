package com.zhangrui.deadlock.synchronines;

/**
 * @Author zr
 * @Date 2019-05-27
 **/
public class User {
    private String name;
    private Integer money;

    public User(String name, Integer money) {
        this.name = name;
        this.money = money;
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

    public void transfer(User user){
        this.money += user.getMoney();
    }
}
