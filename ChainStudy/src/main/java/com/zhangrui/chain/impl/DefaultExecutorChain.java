package com.zhangrui.chain.impl;

import com.zhangrui.chain.Executor;
import com.zhangrui.chain.ExecutorChain;

import java.util.LinkedList;
import java.util.List;

/**
 * Desp:
 * 2018-10-27 18:58
 * Created by zhangrui.
 */
public class DefaultExecutorChain implements ExecutorChain {
    
    private List<Executor> executorList = new LinkedList<Executor>();  
    private int pos = 0;
    
    public void addExecutor(Executor executor){
        executorList.add(executor);
    }
    
    @Override
    public void doExecute() {
        if (executorList.size() > 0 && pos < executorList.size()){
            Executor executor = executorList.get(pos);
            pos++;
            executor.execute(this);
        }
    }
}
