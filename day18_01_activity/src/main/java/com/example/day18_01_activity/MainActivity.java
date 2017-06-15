package com.example.day18_01_activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final  String TAG=MainActivity.class.getSimpleName();
    private int page;
    private TextView textView;

    /**activity创建以执行*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreate");
        textView = (TextView) findViewById(R.id.textId);

        SharedPreferences sp=
                getSharedPreferences("states",
                        Context.MODE_PRIVATE);
        page=sp.getInt("pageKey",0);
        textView.setText("第"+String.valueOf(page)+"页");
    }

    /**activity启动时执行*/
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    /**activity重新启动时执行*/
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart");
    }

    /**activity运行时执行*/
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }
    public void onClick(View v){
        page++;
        textView.setText("第"+String.valueOf(page)+"页");
    }
    /**暂停时执行*/
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
        //状态保存(保存到外存在偏好设置中)
        SharedPreferences sp=
                getSharedPreferences("states", Context.MODE_PRIVATE);
        SharedPreferences.Editor et=sp.edit();
        et.putInt("pageKey",page);
        et.commit();
    }

    /**在后台处于停止状态时执行*/
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }
    /**activity销毁时执行*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }
}
