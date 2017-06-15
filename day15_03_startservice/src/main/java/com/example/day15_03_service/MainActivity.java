package com.example.day15_03_service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**启动service*/
    public void onStartService(View v){
        Intent target=new Intent(this,MyService01.class);
        target.putExtra("uriKey","uri-value-01");
        startService(target);
    }
    /**停止service*/
    public void onStopService(View v){
        Intent target=new Intent(this,MyService01.class);
        stopService(target);
    }
}
