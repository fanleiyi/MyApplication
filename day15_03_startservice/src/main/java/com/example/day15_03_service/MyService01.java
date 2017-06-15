package com.example.day15_03_service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService01 extends Service {
    public static final String TAG=MyService01.class.getSimpleName();

    public MyService01() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**service对象创建时执行(service对象不存在时创建)*/
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
    }
    /**service对象启动时执行(每次启动都会执行)
     * @param intent 意图对象，用于封装数据，传递数据
     * @param flags  表示service的启动类型(是否重新传递intent)
     * @param startId 表示请求id(例如这是第几次执行startService)
     *
     * @return 表示一种粘性,它的值有如下几种
     *  1)START_STICKY (service被非正常kill以后会自动重启，但不会重新传递intent)
     *  2)START_STICKY_COMPATIBILITY(START_STICKY的兼容值)
     *  3)START_NOT_STICKY(service被非正常kill以后不会自动重启)
     *  4)START_REDELIVER_INTENT(service被非正常kill以后会自动重启并且重新传递intent)
     * */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand.intent="+intent);
        Log.i(TAG,"flags="+flags);
        Log.i(TAG,"startId="+startId);
        if(intent!=null){
            Log.i(TAG,intent.getStringExtra("uriKey"));
        }
        return START_REDELIVER_INTENT;
    }
    /**service销毁时执行*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }
}
