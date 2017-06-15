package com.example.day16_04_startbindservice;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements ServiceConnection, SeekBar.OnSeekBarChangeListener {

    private IPlayer player;
    private SeekBar seekBar;
    private AtyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBarId);
        seekBar.setOnSeekBarChangeListener(this);
        // 请求权限
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
       // 绑定service
        bindService(new Intent(this, SongService.class), this, Context.BIND_AUTO_CREATE);
        // 注册一个本地广播
        receiver = new AtyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("action.ATY.RECEIVER01");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);

    }

    private boolean isSeekUpdata = true;
    /**编写广播接受者对象*/
    class AtyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isSeekUpdata) {
                int duration = intent.getIntExtra("durationKey",0);
                int currentPostion = intent.getIntExtra("currentPostionKey", 0);
                seekBar.setMax(duration);//将总时长设置为进度的最大值
                seekBar.setProgress(currentPostion);//设置当前进度为播放的当前时长
            }
        }
    }

    public void onPlayClick(View view){
        if(player!=null) {
            //播放音乐
            player.playMusic();
        }
    }
    public void onPauseClick(View v){
        //暂停音乐
        if(player!=null){
            player.pasueMusic();
        }
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        player = (IPlayer) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }
    //---------------------------------------------------------
    /**seekbar更新时执行*/
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.i("TAG","progress="+progress);
        //可以使用此值更新页面上textview,显示当前播放时间
    }
    /**触摸seekbar时执行*/
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isSeekUpdata = false;
        player.pasueMusic();
    }
    /**停止触摸seekbar时执行*/
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // 获得当前进度
        int progress = seekBar.getProgress();
        player.setCurrentPosition(progress);
        player.playMusic();
        isSeekUpdata=true;

    }
    //------------------------------------------------------------------------
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑之前，再起动service，让service对象不要销毁
        startService(new Intent(this,SongService.class));
        //解绑service(此时只会执行service的onUnbind方法)
        unbindService(this);
        // 解除广播的注册
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
