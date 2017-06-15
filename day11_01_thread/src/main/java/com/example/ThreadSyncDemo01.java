package com.example;

/**
 * Created by pjy on 2017/4/28.
 */

public class ThreadSyncDemo01 {
    /**代表一个任务对象*/
    static class TicketTask implements  Runnable{
        private int ticket=10;//代表10张票
        private String LOCK="LOCK001";
        @Override
        public void run() {
           while(true){
               //当一个线程获得了对象锁，然后进入此代码块执行，其它线程要阻塞等待
               synchronized (LOCK) {//此代码块现在相当于一个原子操作
                   String tName=Thread.currentThread().getName();
                   if (ticket <= 0) break;//一个线程执行到此语句可以被其它线程抢占cpu吗？(加锁以后就不可以了)
                   System.out.println(tName+"-->"+ticket);
                   ticket--;
               }//多线程在共享数据集上要排队执行(线程执行完代码块会释放锁)
           }
        }
    }
    public static void main(String[] args) {
      //1.构建一个任务对象
        TicketTask task=new TicketTask();
      //2.构建线程对象并关联售票任务
        Thread t1=new Thread(task);//Thread(Runnable target)
        Thread t2=new Thread(task);
        Thread t3=new Thread(task);
        Thread t4=new Thread(task);
      //3.启动线程
        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }
}
