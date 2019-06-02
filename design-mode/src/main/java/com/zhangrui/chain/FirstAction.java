package com.zhangrui.chain;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public class FirstAction implements Action {
    @Override
    public void doAction(ActionChain chain) {
        System.out.println("我是第一个Action");
        chain.doAction("我是第一个Action");
    }
}
