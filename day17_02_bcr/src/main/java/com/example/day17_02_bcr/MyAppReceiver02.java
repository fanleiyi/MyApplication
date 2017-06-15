package com.example.day17_02_bcr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAppReceiver02 extends BroadcastReceiver {
    public MyAppReceiver02() {
        Log.i("TAG","MyAppReceiver02");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG","MyAppReceiver02.onReceive");
    }
}
