package com.zhangrui.chain;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public class DefaultChainTest {

    public static void main(String[] args) {
        FirstAction firstAction = new FirstAction();
        SecondAction secondAction = new SecondAction();
        ThirdAction thirdAction = new ThirdAction();

        DefaultActionChain actionChain = new DefaultActionChain();
        actionChain.addAction(firstAction);
        actionChain.addAction(secondAction);
        actionChain.addAction(thirdAction);

        actionChain.startAction();
    }

}
