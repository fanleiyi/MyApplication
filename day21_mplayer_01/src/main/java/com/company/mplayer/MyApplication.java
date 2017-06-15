package com.company.mplayer;

import android.app.Application;
import android.util.Log;

/**
 * Created by pjy on 2017/5/15.
 * 可以将此类型的对象看成是一个全局的Context
 * 1)此对象在app启动时创建
 * 2)此对象在app进行销毁时销毁
 * 可以将此对象理解为一个常住内存的对象
 */

public class MyApplication extends Application {
    public static String user;
    /**在应用创建时执行*/
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG","MyApplication.onCreate");
    }
}
