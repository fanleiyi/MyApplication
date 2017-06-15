package com.company.mplayer.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;

public class SongService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    public static final String PLAY="action.PLAY";
    public static final String PAUSE="action.PAUSE";
    public static final String STOP="action.STOP";
    enum States{PLAY,PAUSE,STOP};
    private States mState=States.STOP;
    private NotificationManager notificationManager;

    private MediaPlayer mPlayer;
    public SongService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action=intent.getStringExtra("actionKey");
        if(PLAY.equals(action)){//处理播放请求
            processPlayRequest(intent);
        }else if(PAUSE.equals(action)){//处理暂停请求
            processPauseRequest();
        }else if(STOP.equals(action)){//处理停止请求
            processStopRequest();
        }else{
            stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }
    private void processPlayRequest(Intent intent){
        //1.创建播放器对象
        if(mPlayer==null){
            mPlayer=new MediaPlayer();
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);
        }else {
            mPlayer.reset();
        }
        //2.根据状态执行播放动作
        if(mState==States.STOP){
            String data=intent.getStringExtra("dataKey");
            try {
                mPlayer.setDataSource(data);
                mPlayer.prepareAsync();
            }catch(Exception e){e.printStackTrace();}
        } else if(mState==States.PAUSE){
            mPlayer.seekTo(currentPlayPostion);
            mPlayer.start();
            mState=States.PLAY;
        }
    }
    private int currentPlayPostion;
    private void processPauseRequest(){
        if(mPlayer.isPlaying()){
            currentPlayPostion=mPlayer.getCurrentPosition();
            mPlayer.pause();
            mState= States.PAUSE;
        }
    }
    private void processStopRequest(){
           stopSelf();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mState=States.STOP;
        if(mPlayer!=null){
            mPlayer.release();
            mPlayer=null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
         mp.start();
         mState=States.PLAY;
         //startForeground(ntf);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //如何写代码要看具体业务
        mp.start();
    }
}
