package com.example.day06_03_zhihu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.day06_03_zhihu.R;

/**
 * Created by tarena on 2017/5/23.
 */
public class NoviceActivity extends Activity implements ViewPager.OnPageChangeListener {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novice);
        button = (Button) findViewById(R.id.button1);
        ViewPager vp = (ViewPager) findViewById(R.id.viewpagerId);
        ImageAdapter adapter=new ImageAdapter();
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(this);
    }
    public void onClick(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    private int imgs[] = {R.drawable.novice1,
            R.drawable.novice2,R.drawable.novice3};

    class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i("TAG", "destroyItem:"+position);
            container.removeView((View)object);
        }
        @Override
        public Object instantiateItem(ViewGroup container,//viewPager
                                      int position) {
            Log.i("TAG", "instantiateItem="+position);
            ImageView imageview = new ImageView(NoviceActivity.this);
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            int img=imgs[position];
            imageview.setImageResource(img);
            container.addView(imageview);
            return imageview;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if(position==2){
            button.setVisibility(View.VISIBLE);
        }else{
            button.setVisibility(View.GONE);
        }
    }
}
