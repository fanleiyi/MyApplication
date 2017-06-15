package com.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by tarena on 2017/5/3.
 */

public class ThreadPoolDemo02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService =
        // 构建只有一个线程的线程池(应用广泛)
        Executors.newSingleThreadExecutor();
        // 构建一个有上限的线程池(应用广泛)
        // Executors.newFixedThreadPool(3);
        // 构建一个没有上限的线程池（具体上限由底层操作系统决定）
        // Executors.newCachedThreadPool();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //...
            }
        });
        // 当需要获得任务的执行结果需要执行以下方法

        Future<Integer> future=
                executorService.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        int result=1;
                        for(int i=5;i>=1;i--){
                            result*=i;
                        }//例如求某个数的阶乘
                        return result;
                    }
                });

        System.out.println(future.get());//阻塞式方法
        executorService.shutdown();
        future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = 1;
                for (int i = 5; i >= 1; i--) {
                    result *= i;
                }//例如求某个数的阶乘
                return result;
            }
        });

        System.out.println(future.get());//阻塞式方法

        executorService.shutdown();
    }
}
