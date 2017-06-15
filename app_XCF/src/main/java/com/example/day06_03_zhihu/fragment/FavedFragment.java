package com.example.day06_03_zhihu.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.day06_03_zhihu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavedFragment extends BaseFragment {


    public FavedFragment() {
        // Required empty public constructor
    }

    private List<TextView> mObjects=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView =  inflater.inflate(R.layout.fragment_faved, container, false);
        initialUI();
        return contentView;
    }

    @Override
    public void initialUI() {

        actionbar = (LinearLayout) contentView.
                findViewById(R.id.actionbar_feved);
        //初始化标题栏
        initialActionbar(-1, "搜索收藏", -1);

        loadObjects();
        //初始化tablayout
        TabLayout tabLayout= (TabLayout) contentView.findViewById(R.id.tabLayoutId);

        for (int i=0;i<mObjects.size();i++){
            tabLayout.addTab(tabLayout.newTab());
        }
        //初始化viewpager
        ViewPager viewPager= (ViewPager)contentView.findViewById(R.id.viewPagerId);
        PagerAdapter adapter=new InnerPagerAdapter();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    String[] pagers = new String[]{"您收藏的菜谱","您收藏的菜单","您的浏览历史"};
    private void loadObjects() {
        for(int i=0;i<3;i++){
            TextView textView=new TextView(getActivity());
            textView.setTextSize(30);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(pagers[i]);
            mObjects.add(textView);
        }
    }
    String[] titles={"菜谱","菜单","历史浏览"};
    class InnerPagerAdapter extends  PagerAdapter{

        /**重写此方法的目的是应用此标题作为TabLayout的标题*/
        @Override
        public CharSequence getPageTitle(int position) {
            Log.i("TAG","position"+position);
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
