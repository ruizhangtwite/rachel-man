package com.zhangrui.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public class DefaultActionChain implements ActionChain {

    private List<Action> actionList;

    private int pos;

    public DefaultActionChain(){
        actionList = new ArrayList<>();
    }

    public void addAction(Action action){
        if (this.actionList != null){
            this.actionList.add(action);
        }
    }

    @Override
    public void doAction(String msg) {
        if (actionList != null && pos < actionList.size()){
            actionList.get(pos++).doAction(this);
        }
    }
}
