package com.example.day17_02_bcr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAppReceiver01 extends BroadcastReceiver {
    public MyAppReceiver01() {
        Log.i("TAG","MyAppReceiver01");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG","MyAppReceiver01.onReceive");
    }
}
