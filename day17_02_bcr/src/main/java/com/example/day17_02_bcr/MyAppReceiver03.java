package com.example.day17_02_bcr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by tarena on 2017/5/9.
 */

public class MyAppReceiver03 extends BroadcastReceiver {
    public MyAppReceiver03() {
        Log.i("TAG","MyAppReceiver03");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG","MyAppReceiver03.onReceive");
    }
}
