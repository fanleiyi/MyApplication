package com.example;

import java.util.Arrays;

class Container{
    /**借助此对象数组存储数据*/
    private Object[] mObjects;
    /**通过此变量记录数组中有效元素(添加的元素)个数*/
    private int size;
    /**通过构造方法初始化数组
     * @param  cap 表示数组的容量
     * */
    public Container(int cap){
        mObjects=new Object[cap];
    }
    /**通过此方法向数组放数据，默认放在size位置*/
    public  synchronized void put(Object data){
        //1.判定数组是否已满，满了则等待且不能再放数据
        while(mObjects.length==size){
           // throw new RuntimeException("conainer is full");
            try{this.wait();}catch(Exception e){}
            //wait方法调用以，此线程会释放锁
        }
        //2.向数组放数据
        mObjects[size]=data;
        try{Thread.sleep(100);}catch(Exception e){}
        //3.有效元素个数加1
        size++;
        //4.通知消费者取数据
        this.notifyAll();
    }
    /**通过此方法从容器中取数据，默认每次取第0个位置的数据*/
    public synchronized  Object take(){
        //1.判定容器是否为空，空则等待且不能取数据
        while(size==0){
            //throw new RuntimeException("conainer is empty");
            try{this.wait();}catch (Exception e){}
        }
        //2.从容器取数据
        Object temp=mObjects[0];
        //3.移动元素
        System.arraycopy(mObjects,1,mObjects,0,size-1);
        //4.有效元素个数减一
        size--;
        //5.初始化size位置元素为null
        mObjects[size]=null;
        //6.通知生产者继续放数据
        this.notifyAll();
        return temp;
    }

    @Override
    public String toString() {
        return mObjects!=null? Arrays.toString(mObjects):null;
    }
}
/**生产者对象:负责向容器中不断的放数据*/
class Producer extends Thread{
    private Container mContainer;
    public Producer(Container container){
        mContainer=container;
    }
    @Override
    public void run() {
        int i=1;
        while(true){
            mContainer.put(i);
            //try{Thread.sleep(500);}catch(Exception e){}
            i++;
        }
    }
}
/**消费者对象:负责向容器中不断的放数据*/
class Consumer extends Thread{
    private Container mContainer;
    public Consumer(Container container){
        mContainer=container;
    }
    @Override
    public void run() {
        while(true){
            System.out.println("take:"+mContainer.take());
            //try{Thread.sleep(1000);}catch(Exception e){}
        }
    }
}

public class ThreadSyncDemo03 {

    public static void main(String[] args) {
        //容器
        Container c=new Container(3);
        //生产者
        Producer pro=new Producer(c);
        //消费者
        Consumer con=new Consumer(c);

        pro.start();
        con.start();

    }
}
