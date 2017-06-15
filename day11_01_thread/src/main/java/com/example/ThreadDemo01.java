package com.example;

public class ThreadDemo01 {
    public static void main(String[] args){
        System.out.println("main.helloworld");
        new Thread(){//构建线程类型的子类类型的对象，并启动
            @Override
            public void run() {
               System.out.println("woker-01.helloworld");
            }
        }.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("woker-02.helloworld");
            }
        }).start();
    }
}
