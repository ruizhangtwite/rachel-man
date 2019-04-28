package com.zhangrui.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Desp:自定义线程池
 * 原理：在线程池内部有一个线程（工作线程池）不断地运行加入到队列中的待处理模块，工作线程池不断地从队列中取模块进行处理；
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
            Worker worker = new Worker(this);
            workers.add(worker);
            worker.start();
        }
    }

    public void execute(Runnable runnable) {
        if (this.working) {
            blockingQueue.offer(runnable);
        }
    }

    public void shutdown() {
        working = false;
        for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getState().equals(Thread.State.BLOCKED) || workers.get(i).getState().equals(Thread.State.WAITING)
                    || workers.get(i).getState().equals(Thread.State.TIMED_WAITING)) {
                workers.get(i).interrupt();
            }
        }
    }


    public static void main(String[] args) throws Exception {
        CustomThreadExecutor executor = new CustomThreadExecutor(80, 5);
        for (int i = 0; i < 60; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始执行了");
            });
        }

        executor.shutdown();
    }


    /**
     * 处理工具类，用于循环从队列中取任务进行执行
     */
    class Worker extends Thread {

        private CustomThreadExecutor exector;

        public Worker(CustomThreadExecutor exector) {
            this.exector = exector;
        }

        @Override
        public void run() {
            Runnable runnable = null;
            if (this.exector.blockingQueue != null) {
                while (CustomThreadExecutor.this.working || this.exector.blockingQueue.size() > 0) {
                    try {

                        if (this.exector.working) {
                            runnable = this.exector.blockingQueue.take();

                        } else {
                            runnable = this.exector.blockingQueue.poll(300, TimeUnit.MILLISECONDS);
                        }

                        if (runnable != null) {
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


