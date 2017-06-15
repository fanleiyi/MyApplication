package employee.karen.tarena.com.imusicmanager.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

import employee.karen.tarena.com.imusicmanager.iconstant.IURL;

public class MusicService extends Service {
    MediaPlayer player = null;//音乐播放控件
    MusicReceiver receiver = null;
    boolean isPause = false;//歌曲的暂停标识

    int seekToTime = 0;

    String pauseUrl="";

    Thread progressThread;//捕获歌曲播放进度的线程
    //一直监控播放器的播放进度
    boolean progressFlag=true;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG:service", "音乐播放服务已启动");
        player = new MediaPlayer();
        //设置音频流的类型
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //为该播放器注册一个监听
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();//开始音乐的播放
            }
        });
        //注册一个出错的监听
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(
                    MediaPlayer mediaPlayer,
                    int i, int i1) {
                Log.i("TAG:error",
                        "播放器出错:what" + i +
                                "extra:" + i1);
                return true;
            }
        });
        //注册一个监听器监听有没有播放完毕一首歌曲
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(
                    MediaPlayer mediaPlayer) {
                //一首歌曲播放完毕会回调该方法
                mediaPlayer.stop();
                //播放下一首歌曲
                //而歌曲的列表是由播放界面持有。
                //发一个广播给广播控件界面实现下一首歌曲的播放
                Intent intent=new Intent(
                        IURL.PLAYNEXT_ACTION);
                sendBroadcast(intent);

            }
        });
        //广播接收器的注册
        registMusicReceiver();


        progressThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressFlag) {
                        if (player.isPlaying()) {
                            //获得播放器的当前的播放进度
                            int cp = player.getCurrentPosition();
                            //获得播放的歌曲的总的长度
                            int duration = player.getDuration();

                            //把捕获到的数据发送给播放控件界面
                            Intent intent = new Intent(IURL.UPDATEPROGRESS_ACTION);
                            intent.putExtra("cp", cp);
                            intent.putExtra("duration", duration);
                            sendBroadcast(intent);

                        }
                        Thread.sleep(1000);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        progressThread.start();
    }

    private void registMusicReceiver() {
        receiver = new MusicReceiver();
        IntentFilter filter = new IntentFilter();
        //订阅播放音乐的广播
        filter.addAction(IURL.PLAYMUSIC_ACTION);
        //订阅暂停广播
        filter.addAction(IURL.PUASEMUSIC_ACTION);
        //订阅进行变化的广播
        filter.addAction(IURL.SEEKUPDATE_ACTION);
        //订阅小组件发来的广播（下一首）
        filter.addAction(IURL.PLAYNEXTWIDGET_ACTION);

        registerReceiver(receiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void play(String musicPath) {
        try {
            if(isPause&&pauseUrl.equals(musicPath)){
                isPause=false;
                player.seekTo(seekToTime);
                player.start();
                seekToTime=0;
                //暂停后接着唱
            }else {
                isPause=false;
                //新加载的歌曲的播放
                player.reset();//进入到IDLE
                //设置数据源
                player.setDataSource(musicPath);

                player.prepareAsync();
            }
          //在服务中发送一个广播给小组件
            //更新小组件的界面(隐藏播放显示暂停)
            Intent intent=new Intent();
            intent.putExtra("musicpath",musicPath);
            intent.setAction(IURL.UPDATEPAUSEWIDGET_ACTION);
            sendBroadcast(intent);




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停正在播放的音乐
     *
     * @param musicPath
     */
    public void pause(String musicPath) {
            if (player.isPlaying()) {
                isPause = true;
                pauseUrl=musicPath;
                //获得当前歌曲演唱的位置
                seekToTime = player.getCurrentPosition();
                player.pause();

                //发一个广播给小组件，(隐藏暂停显示播放)
                Intent intent=new Intent();
                intent.setAction(IURL.UPDATEPLAYWIDGET_ACTION);
                sendBroadcast(intent);

            }
    }

    //定义一个广播接收器
    public class MusicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(
                Context context,
                Intent intent) {
            //当播放广播发来的时候该方法会被回调
            String action = intent.getAction();
            if (IURL.PLAYMUSIC_ACTION.equals(action)) {
                //要播放歌曲
                //获得要播放的歌曲的路径
                String musicPath = intent.getStringExtra("musicpath");
                //开始播放歌曲
                play(musicPath);
            }else if(IURL.PUASEMUSIC_ACTION.equals(action)){
                //要暂停歌曲的播放了
                //获得要暂停的歌曲的路径
                String musicPath=intent.getStringExtra("musicpath");
                //暂停歌曲的播放
                pause(musicPath);
            }else if(IURL.SEEKUPDATE_ACTION.equals(action)){
                //收到的广播的类型是改变歌曲进度的广播
                int progress=intent.getIntExtra("progress",0);
                //改变播放器的进度
                seekTo(progress);
            }else if(IURL.PLAYNEXTWIDGET_ACTION.equals(action)){
                String musipath=intent.getStringExtra("musicpath");
                play(musipath);
            }
        }
    }

    /**
     * 改变播放器的进度
     * @param progress
     */
    private void seekTo(int progress) {
        seekToTime=player.getDuration()*progress/100;
        //改变播放器的进度
        player.seekTo(seekToTime);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        progressFlag=false;
    }
}
