package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pjy on 2017/4/28.
 */

public class ThreadSyncDemo02 {
    static List<String> list=new ArrayList<>();
    public static void main(String[] args) {
        list.add("A");list.add("B");list.add("C");list.add("D");
        System.out.println(list);
        //将线程不安全的集合转换线程安全集合
        //list=Collections.synchronizedList(list);
        Thread t1=new Thread(){
            @Override
            public void run() {
                System.out.println(list.remove(0));
            }
        };
        Thread t2=new Thread(){
            @Override
            public void run() {
                System.out.println(list.remove(0));
            }
        };
        Thread t3=new Thread(){
            @Override
            public void run() {
                System.out.println(list.remove(0));
            }
        };
        Thread t4=new Thread(){
            @Override
            public void run() {
                System.out.println(list.remove(0));
            }
        };

        t1.start();
        t2.start();
        t3.start();
        t4.start();


    }
}
