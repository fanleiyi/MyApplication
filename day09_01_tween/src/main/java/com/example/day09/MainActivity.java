package com.example.day09;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageId);

    }
    public void onAlpha(View v){
        //doAlpha01();
        doAlpha02();
    }

    private void doAlpha01() {
        //构建一个动画对象(1表示完全可见，0表示完全不可见)
        AlphaAnimation a=new AlphaAnimation(1,0);
        //设置动画持续时间
        a.setDuration(3000);
        //让动画停留在最后状态
        a.setFillAfter(true);
        //应用动画对象
        imageView.startAnimation(a);
    }
    /**基于xml方式实现淡入淡出动画*/
    private void doAlpha02(){
        //加载并初始化动画
        Animation a=AnimationUtils.loadAnimation(this,R.anim.alpha_sample_01);
        //应用动画
        imageView.startAnimation(a);
    }
    /**实现旋转动画*/
    public void onRotate(View v){
        //doRotate01();

        doRotate02();
    }

    private void doRotate01() {
        //1.构建动画对象
        //RotateAnimation animation=new RotateAnimation(0,360);
        RotateAnimation animation=
                new RotateAnimation(0,360,
                        imageView.getWidth()/2,imageView.getHeight()/2);
        //2.设置动画
        //设置动画持续时长
        animation.setDuration(3000);
        //设置动画重复模式
        animation.setRepeatMode(Animation.RESTART);
        //设置动画重复次数
        animation.setRepeatCount(3);
        //3.应用动画
        imageView.startAnimation(animation);
    }
    private void doRotate02(){
        //加载并初始化动画
        Animation a=AnimationUtils.
                loadAnimation(this,R.anim.rotate_sample_01);
        //应用动画
        imageView.startAnimation(a);
    }
    public void onScale(View v){
        ScaleAnimation scaleAnimation=
                //new ScaleAnimation(1,2,1,2);
                new ScaleAnimation(1,2,1,2,imageView.getWidth()/2,imageView.getHeight()/2);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(3000);
        imageView.startAnimation(scaleAnimation);
    }
    public void onTranslate(View v){

        doTranslate01();
        //doTranslate02();
    }

    private void doTranslate01() {
        TranslateAnimation translateAnimation=
                new TranslateAnimation(0,
                        imageView.getWidth(),0,0);
        translateAnimation.setDuration(3000);
        translateAnimation.setFillAfter(true);
        imageView.startAnimation(translateAnimation);
    }
    private void doTranslate02(){
        //加载并初始化动画
        Animation a=AnimationUtils.
                loadAnimation(this,R.anim.trans_sample_01);

        //应用动画
        imageView.startAnimation(a);
    }
    public void onImgClick(View v){
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
    }

    public void onSet(View v){
        //doSet01();
        doSet02();
    }

    private void doSet01() {
        AnimationSet set=new AnimationSet(true);
        Animation alpha= AnimationUtils.
                loadAnimation(this, R.anim.alpha_sample_01);
        alpha.setStartOffset(3000);//3000毫秒以后开始执行
        Animation rotate=AnimationUtils.
                loadAnimation(this,R.anim.rotate_sample_01);

        set.addAnimation(rotate);
        set.addAnimation(alpha);
        //指定加速减速模式
        set.setInterpolator(this,android.R.interpolator.accelerate_decelerate);
        set.setFillAfter(true);
        imageView.startAnimation(set);
    }
    private void doSet02(){
        //加载并初始化动画
        Animation a=AnimationUtils.
                loadAnimation(this,R.anim.set_sample_01);

        //应用动画
        imageView.startAnimation(a);
    }

}