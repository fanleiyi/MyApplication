package com.example.day09;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         findViewById(R.id.imageId);
        ImageView imageView = (ImageView) findViewById(R.id.imageId);

        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        // 启动动画
        animationDrawable.start();
    }
}
