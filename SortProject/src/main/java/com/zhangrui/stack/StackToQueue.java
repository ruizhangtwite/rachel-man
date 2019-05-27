package com.zhangrui.stack;

import java.util.Stack;

/**
 * Desp:
 * 2019-04-28 21:03
 * Created by zhangrui.
 */
public class StackToQueue<T> {

    private Stack<T> lStack;
    private Stack<T> rStack;

    public StackToQueue() {
        this.lStack = new Stack<>();
        this.rStack = new Stack<>();
    }

    public synchronized void put(T t) {
        this.lStack.add(t);

    }

    public synchronized T get() {

        T result = null;

        while (!this.lStack.empty()) {
            T t = this.lStack.pop();
            this.rStack.add(t);
        }

        if (!this.rStack.empty()) {
            result = this.rStack.pop();
        }

        while (!this.rStack.empty()) {
            this.lStack.add(this.rStack.pop());
        }

        return result;
    }


    public static void main(String[] args) {
        StackToQueue<Integer> stackToQueue = new StackToQueue<>();
        stackToQueue.put(1);
        stackToQueue.put(10);
        stackToQueue.put(15);
        stackToQueue.put(20);

        System.out.println(stackToQueue.get());
        System.out.println(stackToQueue.get());
        System.out.println(stackToQueue.get());
        System.out.println(stackToQueue.get());
        System.out.println(stackToQueue.get());

    }
}
