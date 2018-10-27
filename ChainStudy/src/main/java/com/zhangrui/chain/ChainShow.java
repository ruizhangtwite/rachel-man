package com.zhangrui.chain;

import com.zhangrui.chain.impl.DefaultExecutorChain;

/**
 * Desp:
 * 2018-10-27 19:01
 * Created by zhangrui.
 */
public class ChainShow {

    public static void main(String[] args) {
        DefaultExecutorChain chain = new DefaultExecutorChain();
        
        chain.addExecutor(new Executor() {
            @Override
            public void execute(ExecutorChain chain) {
                System.out.println("我是第一个Executor");
                chain.doExecute();
            }
        });

        chain.addExecutor(new Executor() {
            @Override
            public void execute(ExecutorChain chain) {
                System.out.println("我是第二个Executor");
                chain.doExecute();
            }
        });
        
        chain.doExecute();
    }
}
