package com.zhangrui.chain;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public class LinkActionChain implements ActionChain {

    private LinkAction head;

    private LinkAction tail;

    private LinkAction excuteAction = null;

    private boolean flag = false;

    public LinkActionChain() {

    }

    public void addAction(Action action) {
        LinkAction linkAction = new LinkAction(action, null);
        if (this.head != null && this.tail != null) {
            this.tail.next = linkAction;
            this.tail = this.tail.next;
        }

        if (this.head == null) {
            this.head = linkAction;
        }
        if (tail == null) {
            this.tail = linkAction;
        }
        if (this.excuteAction == null) {
            this.excuteAction = this.head;
        }


    }

    @Override
    public void doAction(String msg) {

        if (this.flag) {
            this.excuteAction = this.excuteAction.next;
        }

        if (this.excuteAction != null) {
            this.flag = true;
            this.excuteAction.action.doAction(this);
        } else {
            this.flag = false;
            this.excuteAction = this.head;
        }
    }

    static class LinkAction {
        public Action action;
        public LinkAction next;

        public LinkAction(Action action, LinkAction next) {
            this.action = action;
            this.next = next;
        }
    }
}
