package com.example.day15_04_startmusicservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Executors;

public class SongService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    public static final String TAG=SongService.class.getSimpleName();
    public static String PLAY="action.PLAY";
    public static String PAUSE="action.PAUSE";
    public static String STOP="action.STOP";
    public SongService() {}
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    /**媒体音乐播放器对象*/
    private MediaPlayer mPlayer;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate()");

    }

    private void createMediaPlayerIfNeeded() {
        if(mPlayer==null) {
            mPlayer = new MediaPlayer();
            //监听音乐是否播放完成
            mPlayer.setOnCompletionListener(this);
            //监听音乐是否加载完成(异步加载音乐需要使用此监听)
            mPlayer.setOnPreparedListener(this);
        }else{
            mPlayer.reset();//重置
        }
    }
    private void processPlayRequest(){
        //创建媒体播放器
        createMediaPlayerIfNeeded();
        //加载音乐
        try {
            //设置要播放的音乐路径
            mPlayer.setDataSource("/storage/emulated/0/Music/yese.mp3");
            //加载音乐
            //mPlayer.prepare();//主线程加载
            //mPlayer.start();
            mPlayer.prepareAsync();//工作线程加载(加载完成会执行监听器的onPrepare)
            //mPlayer.start();//异步加载不能在此位置播放
        }catch(Exception e){e.printStackTrace();}
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand()");
        String action=intent.getStringExtra("actionKey");
        if(PLAY.equals(action)){
            processPlayRequest();
        }else if(PAUSE.equals(action)){
            mPlayer.pause();
        }else if(STOP.equals(action)){
            stopSelf();//此时会销毁service
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy()");
        if(mPlayer!=null){
            mPlayer.release();
            mPlayer=null;
        }
    }
    /**音乐播放完成以后会自动执行此方法*/
    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i(TAG,"onCompletion");
        if(mPlayer!=null){
            mPlayer.start();
        }
    }

    /**假如是异步加载音乐并且媒体播放器添加了OnPrepareListener监听，
     * 此时会执行如下方法*/
    @Override
    public void onPrepared(MediaPlayer mp) {
        if(mp!=null)mp.start();
    }
}