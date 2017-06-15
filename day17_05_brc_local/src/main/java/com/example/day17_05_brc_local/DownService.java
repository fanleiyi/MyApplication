package com.example.day17_05_brc_local;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by tarena on 2017/5/9.
 */

public class DownService extends IntentService {

    /**自己写的service 中要有一个无参的构造函数*/
    public DownService() {
        super("DownService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//发送广播，然后更新UI
        Intent target=new Intent("action.DOWN01");
        LocalBroadcastManager lManager = LocalBroadcastManager.getInstance(this);
        Log.i("TAG","down-start");
        for (int i = 1;i<=100;i++) {
            try {Thread.sleep(100);} catch (Exception e) {}
            target.putExtra("progressKey",i*1.0f);
            //sendBroadcast(target);//此形式的发送可以跨进程接收
            lManager .sendBroadcast(target);//发送广播，进程内部传递
        }
        target.putExtra("resultKey","down-ok");
        lManager.sendBroadcast(target);
        Log.i("TAG","down-end");



    }
}
