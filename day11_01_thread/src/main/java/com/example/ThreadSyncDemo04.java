package com.example;

/**
 * Created by pjy on 2017/4/28.
 */

public class ThreadSyncDemo04 {
    static String content;
    //psvm
    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                synchronized (ThreadSyncDemo04.class) {
                    content = "helloworld";
                    ThreadSyncDemo04.class.notifyAll();
                }
            }
        }.start();
        //??使用线程通讯解决如下语句的空指针问题
        synchronized (ThreadSyncDemo04.class){
            while (content == null) {
                try {
                    ThreadSyncDemo04.class.wait();
                } catch (Exception e) {

                }
            }
        }
        System.out.println(content.toUpperCase());
    }
}
