package com.example.day09_03_property;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
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
        Button button = (Button) findViewById(R.id.btn_1);
    }


    public void doAlpha(View v) {
        ObjectAnimator o1 =ObjectAnimator.ofFloat(imageView,"alpha",1,0);
        //设置持续时间
        o1.setDuration(3000);
        // 启动动画
        o1.start();
    }

    public void doScale(View v) {
        ObjectAnimator o1 =ObjectAnimator.ofFloat(imageView,"scaleX",1,3);
        ObjectAnimator o2 =ObjectAnimator.ofFloat(imageView,"scaleY",1,3);

        //设置持续时间
        o1.setDuration(3000);
        o2.setDuration(3000);
        // 启动动画
        o1.start();
        o2.start();
    }

    public void doTrans(View v) {
        ObjectAnimator o1 =ObjectAnimator.ofFloat(imageView,"translationX",0,imageView.getWidth());
        //设置持续时间
        o1.setDuration(3000);
        // 启动动画
        o1.start();
    }

    public void doRotate01(View v){
        ObjectAnimator o1 =ObjectAnimator.ofFloat(imageView,"rotationX",0,360);
        ObjectAnimator o2 =ObjectAnimator.ofFloat(imageView,"rotationY",0,360);
        ObjectAnimator o3 =ObjectAnimator.ofFloat(imageView,"rotation",0,360);
        //设置持续时间
        o1.setDuration(3000);
        o2.setDuration(3000);
        o3.setDuration(3000);
        // 启动动画
        o1.start();
        o2.start();
        o3.start();
    }
    public void doRotate(View v) {
        // 1.初始化xml文件对应的属性动画
        ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,
                R.animator.alpha_01);
        // 2.应用属性动画
        objectAnimator.setTarget(imageView);
        // 3.启动属性动画
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(MainActivity.this,"我都转晕了!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    public void onImgClick(View v) {
        Toast.makeText(this,"fuck!",Toast.LENGTH_SHORT).show();
    }


}
