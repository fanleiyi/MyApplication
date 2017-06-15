package com.example.day06_03_zhihu.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.day06_03_zhihu.R;
import com.example.day06_03_zhihu.fragment.CommunityFragment;
import com.example.day06_03_zhihu.fragment.FavedFragment;
import com.example.day06_03_zhihu.fragment.MakertFragment;
import com.example.day06_03_zhihu.fragment.SelfFragment;
import com.example.day06_03_zhihu.fragment.XCFFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialFragments();
    }
    private void initialFragments() {
        radioGroup = (RadioGroup)
                findViewById(R.id.radioGroupId);
        radioGroup.setOnCheckedChangeListener(this);
        onCheckedChanged(radioGroup,R.id.rd_btn1);
    }
    @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                switch (i) {
                    case R.id.rd_btn1:
                        fragmentTransaction.replace(R.id.frame_Main, new XCFFragment(),"frame_xcf_01");
                        break;
                    case R.id.rd_btn2:
                        fragmentTransaction.replace(R.id.frame_Main, new MakertFragment(),"frame_makert_01");
                        break;
                    case R.id.rd_btn3:
                        fragmentTransaction.replace(R.id.frame_Main, new FavedFragment(),"frame_faved_01");
                        break;
                    case R.id.rd_btn4:
                        fragmentTransaction.replace(R.id.frame_Main, new CommunityFragment(),"frame_community_01");
                        break;
                    case R.id.rd_btn5:
                        fragmentTransaction.replace(R.id.frame_Main, new SelfFragment(),"frame_self_01");
                        break;
                }
                fragmentTransaction.commit();
            }


    }

