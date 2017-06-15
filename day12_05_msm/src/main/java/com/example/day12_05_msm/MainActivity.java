package com.example.day12_05_msm;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    private boolean isExit;
    /**此方法用于处理手机上的回退key的点击事件*/
    @Override
    public void onBackPressed() {
        if (!isExit) {
            Toast.makeText(this,"再点一次则退出！",Toast.LENGTH_SHORT).show();
            isExit=true;
            new Handler().postDelayed(new Runnable() {// 发送延迟消息
                @Override
                public void run() {
                    isExit= false;
                }
            },3000);// 3秒以后由主线程执行runnable对象的run方法
        }else {
            super.onBackPressed();
        }
    }
}
