package com.example.mynews.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolProxy {
    ThreadPoolExecutor mExecutor;
    private int mCorePoolSize;//核心池的大小
    private int mMaximumPoolSize;//线程最大线程数

    public ThreadPoolProxy(int mCorePoolSize, int mMaximumPoolSize) {
        this.mCorePoolSize = mCorePoolSize;
        this.mMaximumPoolSize = mMaximumPoolSize;
    }

    //创建线程池
    private void initThreadPoolExecutor() {
        //双重检查加锁 : 只有第一次实例化的时候才启用同步机制,提高了性能
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()){
            synchronized (ThreadPoolProxy.class){
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()){
                    long keepAliveTime = 0;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                    mExecutor=new ThreadPoolExecutor(mCorePoolSize,mMaximumPoolSize,keepAliveTime,unit,workQueue,threadFactory,handler);
                }
            }
        }
    }
    /**
     * 提交任务
     */
    public Future submit(Runnable task){
        initThreadPoolExecutor();
        Future<?> future=mExecutor.submit(task);
        return future;
    }
    /**
     * 执行任务
     */
    public void execute(Runnable task){
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }
    /**
     * 移除任务
     */
    public void remove(Runnable task){
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }
}


