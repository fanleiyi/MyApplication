package com.example.day_18_02_activity;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by pjy on 2017/5/10.
 */

public class DownService extends IntentService {
    public DownService() {
        super("DownService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i("TAG","down start");
        try{
            Thread.sleep(5000);
        }catch (Exception e){}
        Log.i("TAG","down end");

        //下载完更新页面
        //1)方法1：发广播(建议本地)
        //2)方法2: 发消息(handler,activity声明静态handler对象)
        //3)方法3：再次启动activity

        Intent target=new Intent(this,MainActivity.class);
        //在activity的外的其它组件中启动activity一般要加如下标记
        target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        target.putExtra("resultKey","down-ok");
        startActivity(target);

    }
}
