package com.company.mplayer;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.mplayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalMenuFragment extends Fragment {

    private FragmentPagerAdapter mAdapter;
    private ViewPager viewPager;
    public LocalMenuFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_local_menu, container, false);
        setViewPager(view);
        setTabLayout(view);
        return view;
    }
    private void setTabLayout(View view){
        TabLayout tabLayout= (TabLayout) view.findViewById(R.id.tabLayoutId);
     /*   for(int i=0;i<mAdapter.getCount();i++){
            tabLayout.addTab(tabLayout.newTab());//不用
        }*/
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setViewPager(View view){
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerId);
        mAdapter = new LocalFrgAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);

    }
    class LocalFrgAdapter extends  FragmentPagerAdapter{
        public LocalFrgAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0){
                return "歌曲";
            }else if(position==1){
                return "歌手";
            }
            return null;
        }
        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new LocalSongFragment();
            }else if(position==1){
                return new LocalArtistFragment();
            }
            return null;
        }
        @Override
        public int getCount() {
            return 2;
        }
    }

}
