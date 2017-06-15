package com.example.day17;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/**创建此广播以后，需要
 * 1 进行注册
 * 2 注册时还需要指定*/
public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
        Log.i("TAG","MyReceiver");
    }
    /**接收广播时执行*/
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i("TAG","onReceive");


    }
}
