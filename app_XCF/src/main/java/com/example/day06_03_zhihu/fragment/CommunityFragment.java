package com.example.day06_03_zhihu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.day06_03_zhihu.R;
import com.example.day06_03_zhihu.adapter.CommunityAdapter;
import com.example.day06_03_zhihu.entity.Community;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends BaseFragment {


    public CommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         contentView =  inflater.inflate(R.layout.fragment_community, container, false);
        initialUI();
        return contentView;
    }

    @Override
    public void initialUI() {

        ListView listView = (ListView) contentView.findViewById(R.id.listView_community);
        List<Community> communities = new ArrayList<>();
        int[] photos=new int[]{R.mipmap.png_01, R.mipmap.png_02, R.mipmap.png_03, R.mipmap.png_04, R.mipmap.png_05,
                R.mipmap.png_06, R.mipmap.png_07, R.mipmap.png_08, R.mipmap.png_09, R.mipmap.png_10, R.mipmap.png_11, R.mipmap.png_12,};
        String[] names = new String[]{"小老鼠","老牛","小老虎","小兔子","龙","蛇","马","羊","猴子","小鸡","狗","猪",};
        for (int i =0;i<photos.length;i++){
            Community community = new Community();
            community.setPhotoId(photos[i]);
            community.setName(names[i]);
            community.setBody("Body-"+(i+1));
            community.setDate("2017-5-25");
            communities.add(community);
        }
        CommunityAdapter adapter = new CommunityAdapter(getActivity());
        adapter.addDatas(communities,true);
        listView.setAdapter(adapter);

    }
}
