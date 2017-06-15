package com.tarena.karen.weather;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadActivity extends AppCompatActivity {

    @BindView(R.id.imageView_Loading)
    ImageView imageView_Loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        //关联注解框架
        ButterKnife.bind(this);
        //将创建的动画应用到控件
        imageView_Loading.
                setBackgroundResource(R.drawable.loading_amin);
        //获得动画对象
        AnimationDrawable drawable= (AnimationDrawable)
                imageView_Loading.getBackground();
              //开启动画
        drawable.start();

        //添加定时器3秒钟之后实现窗口跳转
        new Handler().postDelayed(
                new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(
                        LoadActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
