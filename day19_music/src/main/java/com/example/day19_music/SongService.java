package com.example.day19_music;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.RemoteViews;

public class SongService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    public static String PLAY="action.PLAY";
    public static String PAUSE="action.PAUSE";
    public static String STOP="action.STOP";
    private String data;
    //记录播放位置
    static int currentPosition;
    private NotificationManager nManager;
    public void setCurrentPosition(int currentPosition) {
        SongService.this.currentPosition=currentPosition;
    }


    public SongService() {
    }

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
        nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //初始化mediaPlayer
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
            mPlayer.setDataSource(data);
            //加载音乐
            mPlayer.prepareAsync();//工作线程加载(加载完成会执行监听器的onPrepare)
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action=intent.getStringExtra("actionKey");
        data = intent.getStringExtra("dataKey");
        if(PLAY.equals(action)){
            processPlayRequest();
            createOrUpdataNotification();
        }else if(PAUSE.equals(action)){
            if(mPlayer.isPlaying()){
                currentPosition=mPlayer.getCurrentPosition();
                mPlayer.pause();
            }
        }else if(STOP.equals(action)){
            stopSelf();//此时会销毁service

        }
        return super.onStartCommand(intent, flags, startId);
    }
    private void createOrUpdataNotification() {

        // 通过此对象封装一个Notification布局对象
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.ntf_layout_01);
        remoteViews.setTextViewText(R.id.textId,"Music-Name-01");
        remoteViews.setOnClickPendingIntent(R.id.closeId, PendingIntent.getService(this,100,new Intent(STOP),// 隐式意图（点击通知栏关闭service）
                PendingIntent.FLAG_UPDATE_CURRENT));
        Notification ntf =
                new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setCustomContentView(remoteViews)
                        .build();
        nManager.notify(1,ntf);
        // 3 将service与Notification对象绑定在一起
        startForeground(1,ntf); // 此时service所在进程为前台进程
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        nManager.cancel(1);
        stopForeground(true);
        if(mPlayer!=null){
            mPlayer.release();
            mPlayer=null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        currentPosition=0;
            if(mPlayer!=null){
                mPlayer.start();
            }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.seekTo(currentPosition);
        mp.start();
    }
}
