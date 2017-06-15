package com.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by tarena on 2017/5/3.
 */

public class ThreadPoolDemo03 {

    public static void main(String[] args) {
        // 有计划的线程池对象(继承ExecutorService)
        ScheduledExecutorService executorService=
        Executors.newScheduledThreadPool(2);

        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        },5, TimeUnit.SECONDS);//代表5秒以后执行

//              executorService.scheduleAtFixedRate(
//                task,// 要执行的任务
//                start,// 初始延迟时间
//                delay,// 每次任务间隔时间（上一次任务有可能还没有结束）
//                TimeUnit);// 时间单位

//        executorService.scheduleWithFixedDelay(
//                task,// 要执行的任务
//                start,// 初始延迟时间
//                delay,// 上一次任务执行时间+delay后，启动下一次任务
//                TimeUnit);// 时间单位

    }
}
