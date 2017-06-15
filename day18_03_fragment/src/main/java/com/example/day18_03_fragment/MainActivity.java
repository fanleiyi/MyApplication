package com.example.day18_03_fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onFrgAdd(View v){//动态添加fragment
        //1.获得fragment对象的管理器
        FragmentManager fragmentManager=
                getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction fragmentTransaction=
                fragmentManager.beginTransaction();
        //3.添加fragment
        fragmentTransaction.add(R.id.container02,//在哪个位置添加
                new Fragment02(),
                "tag_frg_02");//tag为fragment对象的一个唯一标识
        //4.提交事务
        fragmentTransaction.commit();
    }
    public void onFrgRemove(View v){
        //1.获得fragment对象的管理器
        FragmentManager fragmentManager=
                getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction fragmentTransaction=
                fragmentManager.beginTransaction();
        //3.移除fragment
        Fragment frg=fragmentManager.
                findFragmentByTag("tag_frg_02");
        if(frg!=null){
            fragmentTransaction.remove(frg);
        }
        //4.提交事务
        fragmentTransaction.commit();
    }
}
