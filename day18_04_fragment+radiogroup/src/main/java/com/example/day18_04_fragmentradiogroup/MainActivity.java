package com.example.day18_04_fragmentradiogroup;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioId);
        radioGroup.setOnCheckedChangeListener(this);
        RadioButton radioButton = (RadioButton) findViewById(R.id.radioBtn01);
        radioButton.setChecked(true);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // 1 获得fragmentmanager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 2 开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //3.添加fragment
        switch (checkedId) {
            case R.id.radioBtn01 :
            fragmentTransaction.replace(R.id.frame01,//在哪个位置添加
                    new Fragment01(),
                    "tag_frg_01");//tag为fragment对象的一个唯一标识
                break;
            case R.id.radioBtn02 :
            fragmentTransaction.replace(R.id.frame01,//在哪个位置添加
                    new Fragment02(),
                    "tag_frg_02");//tag为fragment对象的一个唯一标识
                break;
        }
        //4.提交事务
        fragmentTransaction.commit();

    }
}
