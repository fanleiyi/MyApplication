package com.example.day12_04_msm;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private Handler handler;
    private Handler postHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textId);
/*        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                textView.setText(String.valueOf(num));
                num--;
                if(num>=0){
                 handler.sendEmptyMessageDelayed(1,1000);
                }
            }
        };//默认关联主线程looper*/

        postHandler=new Handler();

    }
    int num;
  /*  public void onClick(View v){//运行在主线程
        num=10;
        //给主线程发消息
        handler.sendEmptyMessage(1);
    }*/

    public void onClick(View v){
        num=10;
        postHandler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(String.valueOf(num));
                num--;
                if(num>=0){
                    postHandler.postDelayed(this,1000);
                }
            }
        });
    }
}

