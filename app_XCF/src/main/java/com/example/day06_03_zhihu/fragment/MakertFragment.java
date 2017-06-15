package com.example.day06_03_zhihu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.day06_03_zhihu.R;
import com.example.day06_03_zhihu.adapter.MakerAdapter;
import com.example.day06_03_zhihu.entity.Maker;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakertFragment extends BaseFragment {


    public MakertFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         contentView = inflater.inflate(R.layout.fragment_makert, container, false);
        initialUI();
        return contentView;
    }

    @Override
    public void initialUI() {
        actionbar = (LinearLayout) contentView.
                findViewById(R.id.actionbar_makert);
        //初始化标题栏
        initialActionbar(R.drawable.location, "搜索食品相关", R.drawable.cart);

        ListView listView = (ListView) contentView.findViewById(R.id.listView_Maker);
        List<Maker> makers = new ArrayList<>();
        int[] pngs=new int[]{R.drawable.title09,
                R.drawable.title08,
                R.drawable.title06,
                R.drawable.title04,
                R.drawable.title03,
                R.drawable.title01,};

        int[] photos=new int[]{R.mipmap.png_01,
                R.mipmap.png_04,
                R.mipmap.png_05,
                R.mipmap.png_06,
                R.mipmap.png_09,
                R.mipmap.png_12};
        String[] names = new String[]{"小老鼠","小兔子","龙","蛇","猴子","猪",};
        for (int i =0;i<pngs.length;i++) {
            Maker maker = new Maker();
            maker.setPhotoId(photos[i]);
            maker.setBody("Body-"+(i+1));
            maker.setType(1);
            maker.setDate((i*i+3*i+3)+"人做过");
            maker.setName(names[i]);
            maker.setPngId(pngs[i]);
            maker.setTitle("Title-" + (i + 1));
            makers.add(maker);
        }

        MakerAdapter adapter = new MakerAdapter(getActivity());
        adapter.addDatas(makers,true);
        listView.setAdapter(adapter);

    }
}
