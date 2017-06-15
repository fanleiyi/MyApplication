package com.example.day12_02_msw;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;


/**
 * Created by pjy on 2017/5/2.
 */
// 以后此类可以参考HandlerThread
public class WorkerThread extends Thread {

    private Looper wLooper;
    @Override
    public void run() {
        synchronized (this) {
            //1.创建looper(并实现与当前线程的绑定)
            Looper.prepare();
            //2.获得当前线程Looper
            wLooper = Looper.myLooper();
            this.notifyAll();
        }
        // 设置优先级
        Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT);
        //3.迭代消息队列
        Looper.loop();
    }
    /**通过此方法获得当前线程的looper*/
    public Looper getLooper(){
        if (!isAlive())return null;
        synchronized (this) {
            while (isAlive() && wLooper == null) {
                try {
                    this.wait();
                } catch (Exception e) {
                }
            }
        }
        return wLooper;
    }
    public void quit(){
        Looper looper=getLooper();
        if(looper!=null)looper.quit();
    }
}
