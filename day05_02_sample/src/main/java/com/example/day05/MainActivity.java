package com.example.day05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1.获得RecyclerView并进行简单设置
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        // 2.构建并关联LayouManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 3.构建并关联适配器
        List<News> mObjects = loadObjects();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mObjects);
        recyclerView.setAdapter(adapter);
        // 4.添加监听器
    }

    private List<News> loadObjects() {
        List<News> mObjects = new ArrayList<>();
        mObjects.add(new News(R.mipmap.png_01,"News-title-01"));
        mObjects.add(new News(R.mipmap.png_02,"News-title-02"));
        mObjects.add(new News(R.mipmap.png_03,"News-title-03"));
        mObjects.add(new News(R.mipmap.png_04,"News-title-04"));
        mObjects.add(new News(R.mipmap.png_05,"News-title-05"));
        return mObjects;
    }
}
