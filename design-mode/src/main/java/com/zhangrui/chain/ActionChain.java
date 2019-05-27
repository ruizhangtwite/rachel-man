package com.zhangrui.chain;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public interface ActionChain {

    default void startAction(){
        doAction("");
    }

    void doAction(String msg);

}
