package com.tarena.fanly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tarena.fanly.R;
import com.tarena.fanly.adapter.MyFragmetPagerAdapter;
import com.tarena.fanly.fragment.Guide_01Fragment;
import com.tarena.fanly.fragment.Guide_02Fragment;
import com.tarena.fanly.fragment.Guide_03Fragment;
import com.tarena.fanly.fragment.Guide_04Fragment;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends FragmentActivity {

    Guide_01Fragment fragment01=null;
    Guide_02Fragment fragment02=null;
    Guide_03Fragment fragment03=null;
    Guide_04Fragment fragment04=null;
    MyFragmetPagerAdapter adapter = null;
    @BindView(R.id.viewPager_Guide)
    ViewPager viewPager;

    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initialFragments();
        setListener();
    }

    private void setListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position==3){
                    indicator.setVisibility(View.INVISIBLE);
                }else {
                    indicator.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initialFragments() {
        adapter=new MyFragmetPagerAdapter(getSupportFragmentManager());

        fragment01=new Guide_01Fragment();
        fragment02=new Guide_02Fragment();
        fragment03=new Guide_03Fragment();
        fragment04=new Guide_04Fragment();
        viewPager.setAdapter(adapter);

        adapter.addFragment(fragment01);
        adapter.addFragment(fragment02);
        adapter.addFragment(fragment03);
        adapter.addFragment(fragment04);
        viewPager.setCurrentItem(0,false);

        // 低密度 ldpi   120px/1inch(2.54cm)  inch(英寸) px(像素)
        // 中密度 mdpi   160px/1inch(2.54cm)
        // 高密度 hdpi    240px/1inch(2.54cm)
        // 很高密度 xhdpi   320px/1inch(2.54cm)
        // 非常高密度 xxhdpi   480px/1inch(2.54cm)

        // dp绝对单位 160dp=1inch
        // 1dp 在低密度屏幕上 0.75px
        // 1dp 在中密度屏幕上 1.0px
        // 1dp 在高密度屏幕上 1.5px
        // 1dp 在很高密度屏幕上 2.0px
        // 1dp 在非常高密度屏幕上 3.0px

        // 当前运行程序所使用的设备的屏幕密度
        final float density = getResources().getDisplayMetrics().density;

        indicator.setViewPager(viewPager);
        // 5dp在当前设备上所对应的像素值(px)
        indicator.setRadius(5 * density);
        indicator.setFillColor(0xFFFF6633);
        indicator.setStrokeColor(0xFF333333);
        indicator.setStrokeWidth(density);
    }

    public void onClick(View v){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
