package com.example.day09_04_exampe;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // 实现转场动画
    public void onClick(View v) {
        // 方法1
        // startActivity(new Intent(this,OtherActivity.class));
        //重写activity入场和出场动画
        /*overridePendingTransition(
                R.anim.trans_enter_01,
                R.anim.trans_exit_01);*/
      /*overridePendingTransition(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);*/

        //方法2
        Bundle options= ActivityOptions.
                makeCustomAnimation(this,
                        R.anim.trans_enter_01,
                        R.anim.trans_exit_01)
                .toBundle();
        startActivity(
                new Intent(this,OtherActivity.class),
                options);
    }

}
