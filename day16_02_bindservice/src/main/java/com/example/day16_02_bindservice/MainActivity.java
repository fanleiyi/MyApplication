package com.example.day16_02_bindservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ServiceConnection{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick01(View v) {
        Intent target = new Intent(this,MyService01.class);
        int flags = Service.BIND_AUTO_CREATE;
        ServiceConnection connection = this;
        // 绑定Service
        bindService(target,this,flags);
    }
    public void onClick02(View v) {

        unbindService(this);// this 指向ServiceConnection
    }
    /**在Service绑定成功以后自动执行*/
    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        MyService01 myService01= ((MyService01.LocalBinder)binder).getSetvice();
        Log.i("TAG","onServiceConnected");
        myService01.play();


    }

    /**在Service非正常解绑以后会执行*/
    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i("TAG","onServiceDisconnected");
    }
}
