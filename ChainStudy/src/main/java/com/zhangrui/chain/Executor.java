package com.zhangrui.chain;

/**
 * Desp:模拟Filter的Chain模式
 * 2018-10-27 18:55
 * Created by zhangrui.
 */
public interface Executor {
    
    void execute(ExecutorChain chain);
}
