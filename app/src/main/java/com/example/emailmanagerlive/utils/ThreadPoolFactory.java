package com.example.emailmanagerlive.utils;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolFactory {
    static ThreadPoolProxy mNormalThreadPool;
    static ThreadPoolProxy mDownLoadThreadPool;


    /**
     * 得到普通线程池代理对象mNormalThreadPoolProxy
     */
    public static ThreadPoolProxy getNormalThreadPoolProxy() {
        if (mNormalThreadPool == null) {
            synchronized (ThreadPoolFactory.class) {
                if (mNormalThreadPool == null) {
                    mNormalThreadPool = new ThreadPoolProxy(3, 7);
                }
            }
        }
        return mNormalThreadPool;
    }

    /**
     * 得到下载线程池代理对象mDownLoadThreadPoolProxy
     */
    public static ThreadPoolProxy getDownLoadThreadPoolProxy() {
        if (mDownLoadThreadPool == null) {
            synchronized (ThreadPoolFactory.class) {
                if (mDownLoadThreadPool == null) {
                    mDownLoadThreadPool = new ThreadPoolProxy(3, 3);
                }
            }
        }
        return mDownLoadThreadPool;
    }

    public static void main(String[] args) {
//        for (int i = 0; i <20 ; i++) {
//            final int index = (i + 1);
//            ThreadPoolFactory.getNormalThreadPoolProxy().execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("执行线程：" + index);
//                    try {
//                        //模拟线程执行时间，10s
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            //每个任务提交后休眠500ms再提交下一个任务，用于保证提交顺序
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
