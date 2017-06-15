package com.example.day01_layout_mplayer;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private String names[]={"本地歌曲","最近播放","我的收藏"};
    private int images[] = {R.mipmap.actionbar_music_normal,R.mipmap.a4z,R.mipmap.aig};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1.获得listview（以列表形式呈现的item）
        ListView lsv = (ListView) findViewById(R.id.listId);
        // 2.构建适配器(根据传入的数据及item资源文件构建item)：构建item的工厂
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,names){
            // 此方法用于构建item
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView item = (TextView) super.getView(position,convertView,parent);
                // 队item对象的表现形式进行填充（如下内容属于自己的扩展）
                Drawable left= getResources().getDrawable(images[position]);
                left.setBounds(0,0,100,100);// 设置drawable对象的边界
                // 在item左侧位置绘制一个图片
                item.setCompoundDrawables(left,null,null,null);
                return  item;// 包好的饺子
            }
        };
        // 3.关联适配器(目的时通过适配器构建item)
        lsv.setAdapter(adapter);
        //4.添加监听器(监听item对象的点击事件)
        lsv.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this,"显示本地歌曲",Toast.LENGTH_SHORT).show();

    }
}
