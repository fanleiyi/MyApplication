package com.example.day16_01_downservicer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyDownService extends AbsService {

    @Override
    public void onHandleIntent(Intent intent) {
        String tname = Thread.currentThread().getName();
        Log.i("TAG","thred.name" +tname);
        Log.i("TAG","down start" );
        try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
        Log.i("TAG","down start end");
    }
}
