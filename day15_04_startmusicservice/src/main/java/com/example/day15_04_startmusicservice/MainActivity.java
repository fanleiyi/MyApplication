package com.example.day15_04_startmusicservice;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
    }
    public void onSongPlay(View v){
        Intent target=new Intent(this,SongService.class);
        target.putExtra("actionKey",SongService.PLAY);
        startService(target);
    }
    public void onSongPause(View v){
        Intent target=new Intent(this,SongService.class);
        target.putExtra("actionKey",SongService.PAUSE);
        startService(target);
    }
    public void onSongStop(View v){
        Intent target=new Intent(this,SongService.class);
        target.putExtra("actionKey",SongService.STOP);
        startService(target);
    }
}
