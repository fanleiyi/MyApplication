package com.example.day18_03_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment01 extends Fragment {


    public Fragment01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment01, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vpagerId);
        FragmentPagerAdapter adapter =
                // 将fragment中在此嵌套fragment是可以使用getChildFragmentManager()
                new ChildFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        return view;
    }
    class ChildFragmentAdapter extends FragmentPagerAdapter {

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
