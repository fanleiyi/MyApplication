package com.example.day16_03_bindmusicservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.io.PipedReader;

public class SongService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener{
    public SongService() {}
    /**借助此属性记录当前播放位置*/
    private int currentPosition;
    private MediaPlayer mediaPlayer;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化mediaPlayer
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new InnerBinder();
    }
    public class InnerBinder extends Binder implements  IPlayer{

        public void playMusic(){
            play();
        }

        public void PasueMusic() {
            pasue();
        }
    }
    private void play(){
        //重置媒体播放器
        mediaPlayer.reset();
        try {
            //设置要播放的音乐的路径
            mediaPlayer.setDataSource("/storage/emulated/0/Music/wukong.mp3");
            //异步加载音乐
            mediaPlayer.prepareAsync();
        }catch(Exception e){e.printStackTrace();}
    }

    private void pasue(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            currentPosition=mediaPlayer.getCurrentPosition();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        play();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //指定到具体播放位置
        Log.i("TAG","currentPosition="+currentPosition);
        mp.seekTo(currentPosition);
        mp.start();
    }
}
