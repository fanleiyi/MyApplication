package com.example.day16_04_startbindservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SongService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener{

    private ScheduledExecutorService executorService;

    public SongService() {}
    /**借助此属性记录当前播放位置*/
    private int currentPosition;
    private MediaPlayer mediaPlayer;
    private NotificationManager nManager;
    public static final String STOP = "action.STOP";

    @Override
    public void onCreate() {
        super.onCreate();
        executorService = Executors.newSingleThreadScheduledExecutor();
        Log.i("TAG","onCreate");
        //创建NotificationManager
        nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //初始化mediaPlayer
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("TAG","onStartCommand");
        String action = null;
        if(intent !=null ) {
            action = intent.getAction();
        }
        if (STOP.equals(action)) {
            stopSelf();//在service内部停止service
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("TAG","onBind");
        return new InnerBinder();
    }
    public class InnerBinder extends Binder implements  IPlayer{

        public void playMusic(){
            play();
        }

        public void pasueMusic() {
            pasue();
        }
        public void setCurrentPosition(int currentPosition) {
        SongService.this.currentPosition=currentPosition;
        }
    }
    private void play(){
        //重置媒体播放器
        mediaPlayer.reset();
        try {
            //设置要播放的音乐的路径
            mediaPlayer.setDataSource("/sdcard/wukong.mp3");
            //异步加载音乐
            mediaPlayer.prepareAsync();
        }catch(Exception e){e.printStackTrace();}
        // 更新进度
        notifyUpdataSeekBar();
        // 创建并发送一个Notification
        createOrUpdataNotification();
    }

    private void pasue(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            currentPosition=mediaPlayer.getCurrentPosition();
        }
    }

    /**更新seekBar的任务*/
    private Runnable updateSeekBarTask = new Runnable() {
        @Override
        public void run() {
            // 1.获得音乐的总时长
            int duration = mediaPlayer.getDuration();
            // 2.获得当前音乐的播放位置
            int currentPostion = mediaPlayer.getCurrentPosition();
            // 3.将值发送给activity端，更新seekbar
            Intent target = new Intent("action.ATY.RECEIVER01");
            target.putExtra("durationKey",duration);
            target.putExtra("currentPostionKey",currentPostion);
            LocalBroadcastManager.getInstance(SongService.this).sendBroadcast(target);
        }
    };

    /**通知更新seekBar*/
    private void notifyUpdataSeekBar(){
        // 启动工作线程间隔性执行任务
        executorService.scheduleWithFixedDelay(
                updateSeekBarTask,// command
                1, // initialDelay(初始延迟)，此时表1秒以后执行此任务
                1,//delay(任务执行结束，在延迟1秒继续执行下一次任务)
                TimeUnit.SECONDS);// 时间单位（秒）

    }

    private void createOrUpdataNotification() {
        // 通过此对象封装一个Notification布局对象
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.ntf_layout_01);
        remoteViews.setTextViewText(R.id.textId,"Music-Name-01");
        remoteViews.setOnClickPendingIntent(R.id.closeId,PendingIntent.getService(this,100,new Intent(STOP),// 隐式意图（点击通知栏关闭service）
                PendingIntent.FLAG_UPDATE_CURRENT));
        // 1 create Notification
        Notification ntf =
        new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
/*                .setContentTitle("title")
                .setContentText("artist")
                // 设置一个延迟意图(希望点击通知时停止音乐播放)
                .setContentIntent(PendingIntent.getService(this,100,new Intent(STOP),// 隐式意图（点击通知栏关闭service）
                       PendingIntent.FLAG_UPDATE_CURRENT))*/
                //.setContent(remoteViews)
               .setCustomContentView(remoteViews)
                .build();
        //ntf.contentView=remoteViews;
        // 2 notify Notification
        nManager.notify(1,ntf);
        // 3 将service与Notification对象绑定在一起
        startForeground(1,ntf); // 此时service所在进程为前台进程
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("TAG","onUnbind");
        executorService.shutdown();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG","onDestroy");
        //结束service与notification的绑定
        stopForeground(true);
        //退出通知
        nManager.cancel(1);
        //释放资源
        if (mediaPlayer!=null) {
            mediaPlayer.release();
            mediaPlayer=null;
        }

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //播放完成开启循环播放模式
        currentPosition=0;
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
