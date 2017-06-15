package com.example.day17_03_bcr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver02 extends BroadcastReceiver {
    public MyReceiver02() {
        Log.i("TAG","MyReceiver02");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG","onReceive02");
    }
}
