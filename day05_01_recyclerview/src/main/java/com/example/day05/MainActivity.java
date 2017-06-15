package com.example.day05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView= (RecyclerView)
                findViewById(R.id.recyclerViewId);
        //为了性能一般会设置一些此选项
        recyclerView.setHasFixedSize(true);
        //构建一个LayoutManager对象
        LinearLayoutManager layoutManager=
                new LinearLayoutManager(this);
        // GridLayoutManager layoutManager=
        // new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        //构建适配器
        String mObject[]={"Item-01","Item-02","Item-03","Item-04"};
        int images[]={R.mipmap.png_01,R.mipmap.png_02,R.mipmap.png_03,R.mipmap.png_04};

        RecyclerAdapter adapter=new RecyclerAdapter(mObject,images);
        //关联适配器
        recyclerView.setAdapter(adapter);
    }
}




