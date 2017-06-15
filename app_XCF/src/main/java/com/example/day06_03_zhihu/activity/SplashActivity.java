package com.example.day06_03_zhihu.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.day06_03_zhihu.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setMainActivityImageView();
        setSplashVersion();

    }

    public void setMainActivityImageView() {
        // 1.获得一个imageView对象
        ImageView imageView = (ImageView) findViewById(R.id.imageview);
        // 2.启动imageview动画
        // 2.1创建一个淡入淡出效果动画(扩展)
        // 1)构建一个淡入淡出的动画对象
        AlphaAnimation a = new AlphaAnimation(0,1);
        // 2)设置动画时长
        a.setDuration(3000);
        // 3)添加动画监听
        a.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) {
            }

            // 动画结束时执行此方法
            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this,NoviceActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        }
        );        imageView.startAnimation(a);
    }

    public void setSplashVersion() {
        // 1.获得textview
        TextView textView = (TextView) findViewById(R.id.versionText);
        // 2.获得版本相关服务对象
        PackageManager packageManager = getPackageManager();

        try {
            // 3.获得版本信息的对象
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            // 4.通过具体版本初始化textview的值
            textView.setText(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}

