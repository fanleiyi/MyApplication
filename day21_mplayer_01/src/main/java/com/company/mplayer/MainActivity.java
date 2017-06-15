package com.company.mplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.初始化actionBar
        setActionBar();
        //2.初始化drawerLayout
        setDrawerLayout();
    }

    private void setActionBar() {
        //初始化toolbar
        //1.设置toolbar为页面的actionbar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolBarId);
        setSupportActionBar(toolBar);
        //2.初始化toolbar上的radiogroup
        //2.1初始化一个RadioGroup
        RadioGroup radioGroup= (RadioGroup) View.inflate(this,R.layout.toolbar_center_item_01,null);
        //2.2设置radioGroup监听
        radioGroup.setOnCheckedChangeListener(this);
        //2.3toolbar上添加radiogroup
        toolBar.addView(radioGroup,
                new Toolbar.LayoutParams(
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER));
        //2.3设置radiogroup默认选中
        RadioButton radioButton= (RadioButton)
                radioGroup.findViewById(R.id.radio0);
        radioButton.setChecked(true);
        //3.显示logo
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    //初始化drawerLayout
    private void setDrawerLayout(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutId);
        NavigationView navigationView= (NavigationView) findViewById(R.id.my_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        //获得headerview，设置名字
        View headerView=navigationView.getHeaderView(0);
        Log.i("TAG","headerView="+headerView);
        TextView textView= (TextView) headerView.findViewById(R.id.headerTextId);
        Log.i("TAG","header.textView="+textView);
        textView.setText(MyApplication.user!=null?
                MyApplication.user:"游客");
        //初始化ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close);
        //添加监听
        drawerLayout.addDrawerListener(drawerToggle);
        //同步drawerlayout状态
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawerToggle.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.nav_sub_2){
            //1.执行退出操作
            Log.i("TAG","onOptionsItemSelected");
            //2.关闭侧滑菜单

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fragmentManager=
                getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=
                fragmentManager.beginTransaction();
        if(checkedId==R.id.radio0){

            fragmentTransaction.replace(R.id.containerId,
                    new LocalMenuFragment(),
                    "local_menu_fragment");

        }else if(checkedId==R.id.radio1){

        }else if(checkedId==R.id.radio2){

        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.nav_sub_2){
            //1.执行退出操作
            //2.关闭drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(item.getItemId()==R.id.nav_item_1){
            startActivity(new Intent(this,HistoryPlayActivity.class));
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
