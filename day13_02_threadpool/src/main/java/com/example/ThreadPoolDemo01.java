package com.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo01 {
    // 池中核心线程数
    public static final int CORE_POOL_SIZE =1;
    //池中最多线程数
    public static final int MAXIMUM_POOL_SIZE =2;
    // 池中飞核心线程的空闲时间
    public static final int KEEP_ALIVE_SECONDS =1;
    // 时间单位
    public static final TimeUnit KEEP_ALIVE_TIME_UNIT=TimeUnit .SECONDS;
    // 阻塞式任务队列
    public static final BlockingQueue<Runnable> sPoolWorkQueue = new ArrayBlockingQueue<Runnable>(2);
    public static void main(String[] args) {
        // 1.构建线程池
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_SECONDS,
                KEEP_ALIVE_TIME_UNIT,
                sPoolWorkQueue);
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String tname = Thread.currentThread().getName();
                System.out.println(tname+"-task01-start");
                try {Thread.sleep(3000);} catch (InterruptedException e) {}
                System.out.println(tname+"-task01-end");
            }
        });
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String tname = Thread.currentThread().getName();
                System.out.println(tname+"-task02-start");
                try {Thread.sleep(3000);} catch (InterruptedException e) {}
                System.out.println(tname+"-task02-end");
            }
        });
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String tname = Thread.currentThread().getName();
                System.out.println(tname+"-task03-start");
                try {Thread.sleep(3000);} catch (InterruptedException e) {}
                System.out.println(tname+"-task03-end");
            }
        });
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String tname = Thread.currentThread().getName();
                System.out.println(tname+"-task04-start");
                try {Thread.sleep(3000);} catch (InterruptedException e) {}
                System.out.println(tname+"-task04-end");
            }
        });
        poolExecutor.shutdown();// 当池对象不使用了需要关闭，需要等待所有任务执行结束
        // poolExecutor.shutdownNow();// 立刻关闭，可能有些任务还没结束
    }

}
