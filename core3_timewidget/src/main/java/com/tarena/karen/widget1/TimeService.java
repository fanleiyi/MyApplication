package com.tarena.karen.widget1;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeService extends Service {

    private Timer timer=null;

    public TimeService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG","时间服务已启动");
        // 创建一个定时器对象
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date date=new Date();
                SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                String now = format.format(date);

                RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.time_widget);
                remoteViews.setTextViewText(R.id.appwidget_text,now);

                // 获得小组件的服务类的实例对象
                AppWidgetManager manager=AppWidgetManager.getInstance(getApplicationContext());
                ComponentName componentName=new ComponentName(getApplicationContext(),TimeWidget.class);
                manager.updateAppWidget(componentName,remoteViews);
            }
       },0,1000);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        timer=null;
        super.onDestroy();
    }
}
