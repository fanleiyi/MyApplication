package com.example.day12_03_wsm;

import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private WorkerHandler workerHandler;
    private MainHandler mainHandler;
    private MyButton button;
    private HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (MyButton) findViewById(R.id.btn_1);
        // 启动工作线程
        handlerThread = new HandlerThread("woker");
        handlerThread.start();
        // 构建handler关联工作线程looper
        workerHandler = new WorkerHandler(handlerThread.getLooper());
        //构建handler关联当前线程looper(主线程)
        //mainHandler = new MainHandler();
        mainHandler = new MainHandler(Looper.getMainLooper());
    }

    class WorkerHandler extends Handler {
        public WorkerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // 通过此循环模拟下载过程
            for (int i = 1;i<=msg.arg1;i++) {
                try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}

                // 给主线程发消息
                Message newMsg = new Message();
                newMsg.arg1=i;
                mainHandler.sendMessage(newMsg);
            }
        }
    }
    class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            button.setProgress(msg.arg1*1.0f/100);
            button.setTextColor(Color.RED);
            button.setText(msg.arg1+"%");
            button.invalidate();// 重新绘制
        }
    }
    public void onClick(View v) {
        //Message msg=new Message();//不推荐
        Message msg=Message.obtain();//推荐(首先从消息池)
        msg.arg1=100;
        workerHandler.sendMessage(msg);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}
