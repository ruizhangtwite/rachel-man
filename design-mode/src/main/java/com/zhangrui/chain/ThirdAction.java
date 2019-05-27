package com.zhangrui.chain;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public class ThirdAction implements Action {

    @Override
    public void doAction(ActionChain chain) {
        System.out.println("我是第三个Action");
        chain.doAction("我是第三个Action");
    }
}
