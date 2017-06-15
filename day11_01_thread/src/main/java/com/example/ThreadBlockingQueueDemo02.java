package com.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by tarena on 2017/4/28.
 */
class ExecutorThread extends Thread {
    /**借助此容器存储任务*/
    private BlockingQueue<Runnable> bQueue = new ArrayBlockingQueue<Runnable>(5);
    @Override
    public void run() {
        while (true) {
            try {
                //FIFO(先进先出)
                Runnable task = bQueue.take(); // 空了则阻塞
                task.run();
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }
    /**此方法用于向容器中添加任务*/
    public void execute(Runnable task) {
        try {
            bQueue.put(task);// 满了则阻塞
        } catch (InterruptedException e) {e.printStackTrace();}
    }
}
public class ThreadBlockingQueueDemo02 {
    public static void main(String[] args) {
        final Runnable task01 = new Runnable() {
            @Override
            public void run() {
                System.out.println("task01");
                try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
            }
        };
        Runnable task02 = new Runnable() {
            @Override
            public void run() {
                System.out.println("task02");
            }
        };
        ExecutorThread workerThread = new ExecutorThread();
        workerThread.start();

        // 主线程交给thread两个任务(将两个任务放到了工作线程的任务队列中)
        workerThread.execute(task01);
        workerThread.execute(task02);

    }
}
