package com.company.mplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HistoryPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_play);
        //显示向左的那个logo图标并且允许可以被点击
        getSupportActionBar().
                setDisplayHomeAsUpEnabled(true);
    }
}
