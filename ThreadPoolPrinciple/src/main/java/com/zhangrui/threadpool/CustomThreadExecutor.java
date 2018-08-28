package com.zhangrui.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Desp:自定义线程池
 * 2018-08-28 21:28
 * Created by zhangrui.
 */
public class CustomThreadExecutor {

    private BlockingQueue<Runnable> blockingQueue = null;

    private List<Worker> workers = null;

    private volatile boolean working = true;

    public CustomThreadExecutor(int poolSize, int workSize) {
        blockingQueue = new LinkedBlockingQueue<>(poolSize);
        workers = new ArrayList<>(workSize);

        for (int i = 0; i < workSize; i++) {
            Worker worker = new Worker(blockingQueue);
            workers.add(worker);
            worker.start();
        }
    }

    public void execute(Runnable runnable) {
        blockingQueue.offer(runnable);
    }

    public void shutdown() {
        working = false;
        for (int i = 0; i < workers.size(); i++){
            if (workers.get(i).getState().equals(Thread.State.BLOCKED) || workers.get(i).getState().equals(Thread.State.WAITING)
            || workers.get(i).getState().equals(Thread.State.TIMED_WAITING)){
                workers.get(i).interrupt();
            }
        }
    }


    public static void main(String[] args) {
        CustomThreadExecutor executor = new CustomThreadExecutor(80, 5);
        for (int i = 0; i < 60; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "开始执行了");
            });
        }

        executor.shutdown();
    }


    /**
     * 处理工具类，用于循环从队列中取任务进行执行
     */
    class Worker extends Thread {

        private BlockingQueue<Runnable> blockingQueue;

        public Worker(BlockingQueue<Runnable> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            if (this.blockingQueue != null) {
                while (CustomThreadExecutor.this.working || this.blockingQueue.size() > 0) {
                    try {
                        Runnable runnable = blockingQueue.poll();
                        if (runnable != null){
                            runnable.run();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}


