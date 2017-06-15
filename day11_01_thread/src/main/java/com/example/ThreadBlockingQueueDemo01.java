package com.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by tarena on 2017/4/28.
 */

public class ThreadBlockingQueueDemo01 {
    public static void main(String[] args)throws  Exception {

        BlockingQueue<String> bQ = new ArrayBlockingQueue<String>(3);
        //new LinkedBlockingDeque<String>(3);
        bQ.put("A");
        bQ.put("B");
        bQ.put("C");
        //bQ.put("D");//此时阻塞了（wait），满了
        System.out.println(bQ);
        bQ.take();
        bQ.take();
        bQ.take();
        bQ.take();//此时阻塞了(wait),空了
        System.out.println(bQ);
    }
}
