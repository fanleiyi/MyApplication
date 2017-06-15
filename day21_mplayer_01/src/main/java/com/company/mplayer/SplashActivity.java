package com.company.mplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.mplayer.listener.BaseAnimationListener;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
        setSplashImageView();
        setSplashVersion();
    }
    public void setSplashImageView(){
        //1.获得imageview对象
        ImageView imageView= (ImageView)
                findViewById(R.id.imageId);
        //2.启动imageview动画
        //2.1创建一个淡入淡出效果动画(扩展)
        //1)构建一个淡入淡出的动画对象
        AlphaAnimation a=new AlphaAnimation(0,1);
        //2)设置动画持续时长
        a.setDuration(3000);//毫秒
        //3)添加动画监听
        a.setAnimationListener(new BaseAnimationListener(){
            //动画结束时执行此方法
            @Override
            public void onAnimationEnd(Animation animation) {
                 setSplashTimer();
            }
        });
        //2.2应用动画对象
        imageView.startAnimation(a);
    }
    /**实现倒计时操作*/
    int count=3;
    public void setSplashTimer(){
       final TextView textView= (TextView) findViewById(R.id.textId);
       textView.setText(String.valueOf(3));
       final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                count--;
                if(count>=0) {
                    textView.setText(String.valueOf(count));
                    handler.postDelayed(this,1000);
                }else{
                    startActivity(new Intent(SplashActivity.this,
                            GuideActivity.class));
                    finish();
                }
            }
        },1000);
    }
    /**设置版本信息*/
    public void setSplashVersion(){
        //1.获得textview
       TextView textView= (TextView) findViewById(R.id.versionTextId);
       //2.获得版本相关服务对象
       PackageManager packageManager=getPackageManager();
       try {
           //3.获得版本信息对象
           PackageInfo packageInfo =
                   packageManager.getPackageInfo(getPackageName(), 0);
           //4.通过具体版本初始化textview的值
           textView.setText("唱听(version"+packageInfo.versionName+")");
       }catch(Exception e){e.printStackTrace();}
    }
}
