package com.zhangrui.deadlock.reentrant;

/**
 * @Author zr
 * @Date 2019-05-27
 **/
public interface TransferMoney {

    void transfer(User from, User to);
}
