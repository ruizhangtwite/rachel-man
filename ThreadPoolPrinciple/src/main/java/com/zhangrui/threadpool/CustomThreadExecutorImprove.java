package com.zhangrui.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Desp:自定义线程池
 * 原理：在线程池内部有一个线程（工作线程池）不断地运行加入到队列中的待处理模块，工作线程池不断地从队列中取模块进行处理；
 * 2018-08-28 21:28
 * Created by zhangrui.
 */
public class CustomThreadExecutorImprove {

    private int coreSize;
    private int maxSize;
    private ArrayBlockingQueue<Runnable> workQueue;
    private List<Worker> workers;
    private volatile boolean isRunning = true;
    private AtomicInteger atomCoreSize = new AtomicInteger(0);
    private AtomicInteger atomMaxSize = new AtomicInteger(0);

    public CustomThreadExecutorImprove(int coreSize, int workQueueSize, int maxSize) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;

        workQueue = new ArrayBlockingQueue<>(workQueueSize);
        workers = new ArrayList<>(coreSize);

    }

    public void execute(Runnable r) {
        if (isRunning) {
            // 核心线程数不满，创建线程
            if (atomCoreSize.get() < coreSize) {
                Worker worker = new Worker(this, true);
                worker.start();
                workers.add(worker);
                atomCoreSize.incrementAndGet();
                atomMaxSize.incrementAndGet();
            } else if (workQueue.offer(r)) { //核心线程数满的话，放在阻塞队列里面

            } else { //阻塞队列满了后，创建非核心线程
                if (atomMaxSize.get() < maxSize) {
                    Worker worker = new Worker(this, false);
                    worker.start();
                    workers.add(worker);
                    atomMaxSize.incrementAndGet();
                }

            }

        }
    }

    public void shutdown() {
        this.isRunning = false;
        for (int i = 0; i < workers.size(); i++) {
            Worker worker = workers.get(i);
            if (worker.getState().equals(Thread.State.BLOCKED) || worker.getState().equals(Thread.State.WAITING)) {
                worker.interrupt();
            }
        }
    }

    public static void main(String[] args) {
        CustomThreadExecutorImprove exector = new CustomThreadExecutorImprove(3, 100, 6);
        for (int i = 0; i < 500; i++) {
            int k = i;
            exector.execute(() -> {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                System.out.println(Thread.currentThread().getName() + "---------->" + k);
            });
        }

        exector.shutdown();
    }


    static class Worker extends Thread {

        private CustomThreadExecutorImprove exector;
        private boolean core;

        public Worker(CustomThreadExecutorImprove exector, boolean core) {
            this.exector = exector;
            this.core = core;
        }

        @Override
        public void run() {

            while (this.exector.isRunning || this.exector.workQueue.size() > 0) {
                
                Runnable r = null;
                try {
                    //核心线程一直不回收，因此用take()
                    if (this.exector.isRunning && this.core) {
                        r = this.exector.workQueue.take();
                    } else {
                        //其他用poll()，并设置超时时间，即是线程的keepAlive时间
                        r = this.exector.workQueue.poll(30, TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException e) {
                }

                if (r != null) { //核心线程和非核心线程都在获取到待执行的线程后执行
                    r.run();
                } else {
                    if (!this.core) {
                        this.exector.workers.remove(this); //移出非核心线程
                        break; //非核心线程直接break
                    }
                }

            }


        }

    }

}


