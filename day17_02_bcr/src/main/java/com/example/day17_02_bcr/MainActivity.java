package com.example.day17_02_bcr;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MyAppReceiver03 receiver03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 动态注册receiver03
        receiver03 = new MyAppReceiver03();
        IntentFilter filter = new IntentFilter();
        // 可以将action理解为这个广播对象的唯一标识
        filter.addAction("app.action.R03");
        // 注册到一个系统服务
        registerReceiver(receiver03,filter);
    }

    public void onClick01(View v) {// 发送不指定接收权限的广播
        String action = "app.action.R01";
        // intent构造方法中传递的action必须对应receiver注册时的action相同
        Intent target = new Intent(action);
        // 向指定action发送广播
        sendBroadcast(target);
    }
    public void onClick02(View v) {
        String action = "app.action.R02";
        // intent构造方法中传递的action必须对应receiver注册时的action相同
        Intent target = new Intent(action);
        // 向指定action发送广播
        sendBroadcast(target,"app.permission.p01");
    }
    public void onClick03(View v) {
        String action = "app.action.R03";
        // intent构造方法中传递的action必须对应receiver注册时的action相同
        Intent target = new Intent(action);
        // 向指定action发送广播
        sendBroadcast(target);// 底层会根据action找到对应的receiver，然后执行receiver的onReceiver方法
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 说明一般广播采用动态注册时，要在注册的组件（例如activicty）销毁时，解除注册
        unregisterReceiver(receiver03);
    }
}
