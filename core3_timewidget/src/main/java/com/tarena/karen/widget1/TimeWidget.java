package com.tarena.karen.widget1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class TimeWidget extends AppWidgetProvider {

    //两种情况下会被回调
    //1.更新时间到来的时候,系统会发一个更新广播
    //2.每次添加同种类型的小组件到桌面一上时会发更新广播
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i("TAG:", "onUpdate");
        // 获得当前系统时间
        Date date = new Date();
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        String now = format.format(date);
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.time_widget);
        remoteViews.setTextViewText(R.id.appwidget_text,now);
        appWidgetManager.updateAppWidget(appWidgetIds,remoteViews);
    }

    //第一个同种类型的小组件被创建时回调该方法
    //发一个可用的广播
    @Override
    public void onEnabled(Context context) {
        Log.i("TAG:", "onEnabled");
        Intent intent =new Intent(context,TimeService.class);
        context.startService(intent);
    }

    //同种类型的最后一个小组件被删除时会回调该方法
    @Override
    public void onDisabled(Context context) {
        Log.i("TAG:", "onDisabled");
        Intent intent =new Intent(context,TimeService.class);
        context.stopService(intent);
    }

    //每一个同种类型的小组件被删除时会回调
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.i("TAG:", "onDeleted");
    }
}

