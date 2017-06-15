package com.example.day03;

import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lsv;
    private DrawerLayout drawerLayout;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        lsv = (ListView) findViewById(R.id.startListMenuId);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        ArrayAdapter<String> adapter=
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        new String[]{"Item01","Item02","Item03"});
        lsv.setAdapter(adapter);
        lsv.setOnItemClickListener(this);
    }

    /**
     *
     * @param parent 代表当前adapterview对象（ListView）
     * @param view 当前点击的itemview对象
     * @param position 当前点击的item的位置
     * @param id 当前点击的item的id（这个值目前与position相同）
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 1.获得点击的item对象
        String text= (String) parent.getItemAtPosition(position);
        // 2.修改内容面板上的textview的值
        tv.setText(text);
        // 3.关闭当前侧滑菜单
        drawerLayout.closeDrawer(GravityCompat.START);

    }
}
