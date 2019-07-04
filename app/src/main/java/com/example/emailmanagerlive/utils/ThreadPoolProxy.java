package com.example.emailmanagerlive.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolProxy {
    private int mCorePoolSize;
    private int mMaximumPoolSize;
    private ThreadPoolExecutor mExecutor;

    public ThreadPoolProxy(int mCorePoolSize, int mMaximumPoolSize) {
        this.mCorePoolSize = mCorePoolSize;
        this.mMaximumPoolSize = mMaximumPoolSize;
    }

    private void setUpThreadPoolProxy() {
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
            synchronized (ThreadPoolProxy.class) {
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                    long keepAliveTime = 3000;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    //无界队列
                    BlockingQueue workQueue = new ArrayBlockingQueue<>(3);
//                    BlockingQueue workQueue = new LinkedBlockingDeque<>();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    //丢弃任务，但是不抛出异常
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                    //丢弃任务，但是抛出异常
//                    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
                    mExecutor = new ThreadPoolExecutor(
                            mCorePoolSize,
                            mMaximumPoolSize,
                            keepAliveTime,
                            unit,
                            workQueue,
                            threadFactory,
                            handler);
                }
            }
        }
    }

    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        setUpThreadPoolProxy();
        mExecutor.execute(task);
    }

    /**
     * 提交任务
     */
    public Future submit(Runnable task) {
        setUpThreadPoolProxy();
        return mExecutor.submit(task);
    }

    /**
     * 移除任务
     */
    public void remove(Runnable task) {
        setUpThreadPoolProxy();
        mExecutor.remove(task);
    }
}
