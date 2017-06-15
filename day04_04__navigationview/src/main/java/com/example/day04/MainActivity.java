package com.example.day04;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private ImageView imageView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        Toolbar toolbar= (Toolbar)findViewById(R.id.my_toolbar);
        // 让Toolbar替换Actionbar
        setSupportActionBar(toolbar);
       imageView = (ImageView) findViewById(R.id.img1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
                    ObjectAnimator o1 =ObjectAnimator.ofFloat(imageView,"rotationX",0,360);
                    ObjectAnimator o2 =ObjectAnimator.ofFloat(imageView,"rotationY",0,360);
                    ObjectAnimator o3 =ObjectAnimator.ofFloat(imageView,"rotation",0,360);
                    //设置持续时间
                    o1.setDuration(3000);
                    o2.setDuration(3000);
                    o3.setDuration(3000);
                    // 启动动画
                    o1.start();
                    o2.start();
                    o3.start();
           }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.my_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                  switch (item.getItemId()) {
                      case R.id.nav_item_1 :
                          Toast.makeText(MainActivity.this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();
                          drawerLayout.closeDrawer(navigationView);
                          break;
                      case R.id.nav_item_2 :
                          Toast.makeText(MainActivity.this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();
                          drawerLayout.closeDrawer(navigationView);
                          break;
                  }
                  return true;
              }
        }
        );
    }
}
