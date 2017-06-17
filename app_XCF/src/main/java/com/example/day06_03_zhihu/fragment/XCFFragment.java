package com.example.day06_03_zhihu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.day06_03_zhihu.R;
import com.example.day06_03_zhihu.adapter.XCFAdapter;
import com.example.day06_03_zhihu.entity.XCF;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class XCFFragment extends BaseFragment {


    private XCFAdapter adapter;
    private List<XCF> xcfs;

    public XCFFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contentView  = inflater.inflate(R.layout.fragment_xcf, container, false);
        initialUI();
       return contentView;

    }

    @Override
    public void initialUI() {
        actionbar = (LinearLayout) contentView.
                findViewById(R.id.actionbar_XCF);
        //初始化标题栏
        initialActionbar(R.drawable.createrecipeicon, "搜索菜谱、食材", R.drawable.buy_list_button);

        xcfs = new ArrayList<>();

        int[] pngs=new int[]{R.drawable.title01,
                R.drawable.title02,
                R.drawable.title03,
                R.drawable.title04,
                R.drawable.title05,
                R.drawable.title06,
                R.drawable.title07,
                R.drawable.title08,
                R.drawable.title09,
                R.drawable.title10,};
        for (int i =0;i<pngs.length;i++){
            XCF xcf = new XCF();
            xcf.setPrint(pngs[i]);
            xcf.setTitle("Title-"+(i+1));
            xcfs.add(xcf);
        }
        PullToRefreshListView ptrListView = (PullToRefreshListView) contentView.findViewById(R.id.ptrl_xcf);
        ListView listView =ptrListView.getRefreshableView();
        View headview=View.inflate(getActivity(), R.layout.image_btn_xcf_01, null);
        listView.addHeaderView(headview);

        adapter = new XCFAdapter(getActivity());
        adapter.addDatas(xcfs,true);
        listView.setAdapter(adapter);
    }
}
