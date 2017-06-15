package com.example.day06;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TextView> mObjects=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据
        loadObjects();
        //初始化tablayout
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabLayoutId);

        for (int i=0;i<mObjects.size();i++){
            tabLayout.addTab(tabLayout.newTab());
        }
        //初始化viewpager
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewPagerId);
        PagerAdapter adapter=new InnerPagerAdapter();
        viewPager.setAdapter(adapter);
        //设置与viewpager之间的互动,通过tablayout会自动通过viewpager中的getPageTitle方法获得标题设置到tab上
        tabLayout.setupWithViewPager(viewPager);


    }

    private void loadObjects() {
        for(int i=0;i<3;i++){
            TextView textView=new TextView(this);
            textView.setTextSize(30);
            textView.setText("Pager-"+i);
            mObjects.add(textView);
        }
    }
    String[] titles={"推荐","军事","财经"};
    class InnerPagerAdapter extends  PagerAdapter{

        /**重写此方法的目的是应用此标题作为TabLayout的标题*/
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return mObjects.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==mObjects.get((Integer) object);
        }
        @Override
        public Object instantiateItem(ViewGroup container,
                                      int position) {

            View item=mObjects.get(position);
            container.addView(item);
            return position;//key/item(view)
        }

        @Override
        public void destroyItem(ViewGroup container,
                                int position, Object object) {
            container.removeView(mObjects.get((Integer)object));
        }
    }
}