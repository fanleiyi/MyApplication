package com.example;

/**
 * Created by pjy on 2017/4/28.
 */

public class ThreadDemo02 {
    static String content;
    //psvm (快捷键)
    public static void main(String[] args) {
        //System.out.println("main thread");
        //获得当前线程
        final Thread mainThread=Thread.currentThread();
        new Thread(){
            @Override
            public void run() {//运行在工作线程
                content="helloworld";
                //唤醒主线程(假如还在休眠)
                mainThread.interrupt();
            }
        }.start();//在主线程启动，何时运行?获得了cpu以后
        //当content的值为空时，暂时阻塞主线程(借助循环)
        if(content==null){
            try{Thread.sleep(3000);}//主线程阻塞
            catch(Exception e){}
        }
        System.out.println(content.toUpperCase());

    }
}
