package com.tarena.fanly.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.tarena.fanly.R;
import com.tarena.fanly.constant.Constant;
import com.tarena.fanly.util.SPUtil;

public class StartActivity extends Activity {

    SPUtil spUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        spUtil=new SPUtil(this);
        // 界面停留几秒钟
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 根据是否是第一次使用进行相应的界面跳转
                Intent intent;
                if (spUtil.isFirst()){
                    // 向新手指导页跳转
                    intent = new Intent(StartActivity.this,GuideActivity.class);
                    spUtil.setFirst(false);
                }else {
                    // 向主页面跳转
                    intent = new Intent(StartActivity.this,MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },2000);

    }
}
