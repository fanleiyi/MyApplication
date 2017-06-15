package com.example.day19_music;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, NavigationView.OnNavigationItemSelectedListener {

    private ImageView imageView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
        setTitle("");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarId);
        // 让Toolbar替换Actionbar
        setSupportActionBar(toolbar);

        RadioGroup radioGroup = (RadioGroup) View.inflate(this, R.layout.toolbar_center_item_01, null);
        radioGroup.setOnCheckedChangeListener(this);
        toolbar.addView(radioGroup,
                new Toolbar.LayoutParams(
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER));
        RadioButton radioButton1 = (RadioButton) radioGroup.findViewById(R.id.radio1);
        radioButton1.setChecked(true);
        //3.显示logo
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.my_drawerId);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        // 添加监听
        drawerLayout.addDrawerListener(drawerToggle);
        // 同步drawerlayout状态
        drawerToggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.navigationId);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //添加本地音乐Fragment（LocalSongFragment）
        //获得fragment对象的管理器
        FragmentManager fragmentManager=
                getSupportFragmentManager();
        //开启事务
        FragmentTransaction fragmentTransaction=
                fragmentManager.beginTransaction();

        switch (checkedId){
            case R.id.radio1:
        //添加fragment
        fragmentTransaction.add(R.id.frameId,//在哪个位置添加
                new LocalMenuFragment(), "local_menu_fragment");//tag为fragment对象的一个唯一标识
                break;

            case R.id.radio2:

                break;
            case R.id.radio3 :

                break;
        }
        //提交事务
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_1 :
                startActivity(new Intent(this,HistoryPlayActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_sub_2 :
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }


}
