package com.example.day18_04_fragmentradiogroup;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment01 extends Fragment {


    public ViewPager viewPager;

    public Fragment01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment01, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.vpagerId);
        FragmentPagerAdapter adapter =
                // 将fragment中在此嵌套fragment是可以使用getChildFragmentManager()
                new ChildFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayoutId);
        //设置与viewpager之间的互动,通过tablayout会自动通过viewpager中的getPageTitle方法获得标题设置到tab上
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
    String[] titles={"frg01","frg02"};
    class ChildFragmentAdapter extends FragmentPagerAdapter {

        /**重写此方法的目的是应用此标题作为TabLayout的标题*/
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        public ChildFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        /**根据位置position返回position，此方法在初始化item的instantiateItem这个方法中进行调用*/
        @Override
        public Fragment getItem(int position) {
            if (position ==0){
                return new ChildFragment01();
            }else if (position == 1){
                return new ChildFragment02();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}


