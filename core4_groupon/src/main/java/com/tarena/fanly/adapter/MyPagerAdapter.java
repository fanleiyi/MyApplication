package com.tarena.fanly.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarena on 2017/6/16.
 */

public class MyPagerAdapter extends PagerAdapter {

    List<View> views = new ArrayList<>();
    Context context;

    public MyPagerAdapter(Context context) {
        this.context=context;
    }

    public void addViewId(int viewId){
        if (viewId>0){
            View view= LayoutInflater.from(context).inflate(viewId,null);
            views.add(view);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(views.get(position),0);
        return views.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
