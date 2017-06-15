package com.example.day16_01_downservicer;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

public abstract class AbsService extends Service {
    public AbsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    /**此线程在启动时会自动创建一个looper*/
    private HandlerThread handlerThread;
    private ServiceHandler serviceHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        handlerThread = new HandlerThread("workerThread");
        handlerThread.start();
        serviceHandler = new ServiceHandler(handlerThread.getLooper());
    }

    class ServiceHandler extends Handler{
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // 1.执行耗时操作
            onHandleIntent((Intent)msg.obj);
            // 2.停止service
            stopSelf(msg.arg1); // startId

        }
    }
    public abstract void onHandleIntent(Intent intent);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = Message.obtain();
        msg.obj = intent;
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}
