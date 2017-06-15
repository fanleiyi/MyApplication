package employee.karen.tarena.com.imusicmanager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import employee.karen.tarena.com.imusicmanager.R;
import employee.karen.tarena.com.imusicmanager.entity.Music;
import employee.karen.tarena.com.imusicmanager.iconstant.IURL;
import employee.karen.tarena.com.imusicmanager.manager.DownLoadManager;
import employee.karen.tarena.com.imusicmanager.manager.MyImageLoader;
import employee.karen.tarena.com.imusicmanager.view.DiskView;

public   class PlayActivity extends BaseActivity implements View.OnClickListener{

    ImageView imageView_Left;
    TextView textView_Title;
    ImageView imageView_Right;

    ImageView imageView_Favo;
    ImageView imageView_DownLoad;
    TextView textView_Current;
    TextView textView_Duration;
    SeekBar seekBar;

    ImageButton imageButton_Previous;
    ImageButton imageButton_Pause;
    ImageButton imageButton_Next;

    DiskView diskView;
    int seekProgress;//记录进度条的进度


    Music music=null;//当前要播放的音乐信息
    List<Music> musics;//用来保存从音乐列表传来的集合
    int currentPosition;//当前要播放的音乐的位置

    boolean isPlay=false;

    ProgressReceiver receiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initialDatas();
        initialUI();
        setListener();

    }

    private void setListener() {
        imageButton_Previous.setOnClickListener(this);
        imageButton_Pause.setOnClickListener(this);
        imageButton_Next.setOnClickListener(this);
        imageView_DownLoad.setOnClickListener(this);
        //为歌曲的进度条注册监听
        seekBar.setOnSeekBarChangeListener(new MyOnSeekBarListener());

    }

    private  class MyOnSeekBarListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar,
                                      int progress, boolean b) {
            seekProgress=progress;
            //获得当前播放的歌曲的总的时长
            String duration=
                    music.getDurationtime();
            try{
                Date time=new SimpleDateFormat("mm:ss").parse(duration);
                long currentTime=time.getTime()*progress/100;
                textView_Current.setText(
                        new SimpleDateFormat("mm:ss").
                        format(currentTime));
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //发一个广播给服务，
            // 把当前歌曲的播放进度发送过去
            //服务接到进度之后使用进度改变MediaPlayer的进度
            Intent intent=new Intent(IURL.SEEKUPDATE_ACTION);
            intent.putExtra("progress",seekProgress);
            sendBroadcast(intent);


        }
    }

    private void initialDatas() {
        Intent intent=getIntent();
        musics=(ArrayList<Music>)
                intent.getSerializableExtra("musics");
        currentPosition=intent.getIntExtra("position",0);
        music=musics.get(currentPosition);
    }

    @Override
    public void initialUI() {
        actionbar = (LinearLayout) findViewById(R.id.actionBar_Play);
        initialActionbar(
                R.drawable.back,
                music.getName(),
                R.drawable.statics);
        imageView_Left =
                (ImageView) actionbar.
                findViewById(R.id.imageView_Actionbar_Left);
        imageView_Right = (ImageView) actionbar.
                findViewById(R.id.imageView_Actionbar_Right);
        textView_Title= (TextView) actionbar.
                findViewById(R.id.textView_Actionbar_Title);
        imageView_Favo= (ImageView)
                findViewById(R.id.imageView_Play_Favo);
        imageView_DownLoad= (ImageView) findViewById(
                R.id.imageView_Play_DownLoad);

        textView_Current= (TextView) findViewById(
                R.id.textView_Play_Current);
        textView_Duration= (TextView) findViewById(
                R.id.textView_Play_Duration);



        seekBar= (SeekBar) findViewById(R.id.seekBar_Play);

        imageButton_Previous= (ImageButton) findViewById(
                R.id.imageButton_Play_Previous);
        imageButton_Pause= (ImageButton) findViewById(
                R.id.imageButton_Play_Pause);
        imageButton_Next= (ImageButton) findViewById(
                R.id.imageButton_Play_Next);

        diskView= (DiskView) findViewById(R.id.diskView_Play);

        imageView_Left.setColorFilter(Color.WHITE,
                PorterDuff.Mode.SRC_ATOP);
        imageView_Right.setColorFilter(Color.WHITE,
                PorterDuff.Mode.SRC_ATOP);
        imageView_Favo.setColorFilter(Color.RED,
                PorterDuff.Mode.SRC_ATOP);
        imageView_DownLoad.setColorFilter(Color.WHITE,
                PorterDuff.Mode.SRC_ATOP);

        textView_Current.setTextColor(Color.WHITE);
        textView_Duration.setTextColor(Color.WHITE);

        //设置当前音乐的总的播放时长
        textView_Duration.setText(music.getDurationtime());

    }

    @Override
    protected void onResume() {
        super.onResume();
        //发一个广播给服务，开始播放歌曲
        play();
        registProgressReceiver();
    }

    private void registProgressReceiver() {
        receiver=new ProgressReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(IURL.PLAYNEXT_ACTION);
        filter.addAction(IURL.UPDATEPROGRESS_ACTION);
        registerReceiver(receiver,filter);


    }

    private void play() {
        isPlay=true;//播放标识为真
        //把按钮的图片设置为暂停图片
        imageButton_Pause.setImageResource(R.drawable.pause);
        diskView.startRotation();

        //获得当前要播放的音乐对象
        music=musics.get(currentPosition);
        String imageUrl=IURL.ROOT+music.getAlbumpic();

        textView_Title.setText(music.getName());
        textView_Duration.setText(music.getDurationtime());
        //设置唱片的专辑图片
        MyImageLoader.setBitmapFromCache(this,
                diskView.getAlbumpic(),
                imageUrl);

        String musicPath=IURL.ROOT+music.getMusicpath();
        //发一个播放的广播给服务
        Intent intent=new Intent(IURL.PLAYMUSIC_ACTION);
        intent.putExtra("musicpath",musicPath);
        sendBroadcast(intent);
    }
    //进行运行时权限的授权
    private void checkPermission(){
        if(ContextCompat.
                checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,new String[]{Manifest.
                    permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            downLoadMusic();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButton_Play_Pause:
                //向服务发播放或暂停的指令
                if(isPlay){
                    //暂停
                    pause();
                }else{
                    //播放
                    play();
                }
                break;
            case R.id.imageButton_Play_Next:
                //播放下一首歌曲
                next();
                break;
            case R.id.imageButton_Play_Previous:
                //播放上一首歌曲
                previous();
                break;
            case R.id.imageView_Play_DownLoad:
                //downLoadMusic();
                checkPermission();
                break;
        }
    }

    //实现音乐文件的下载
    private void downLoadMusic() {
        AlertDialog.Builder builder=
                new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.favo);
        builder.setTitle("系统提示");
        builder.setMessage("确定要下载当前歌曲吗?");
        builder.setNegativeButton("再想想",null);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //获得要下载的音乐文件的路径
                String musicPath=IURL.ROOT+music.getMusicpath();
                //获得要下载的音乐文件的名字
                String fileName=musicPath.substring(musicPath.lastIndexOf("/")+1);

                //实现音乐文件的下载
                DownLoadManager.downLoadFile(PlayActivity.this,
                        musicPath,fileName);
            }
        });
        builder.create().show();

    }

    /**
     * 播放上一首歌曲
     */
    private void previous() {
        //当歌曲不是第一首的时候实现索引的自减
        if(currentPosition>0){
            currentPosition--;
        }else{
            //当正在播放的歌曲是第一首的时候，
            // 上一首播放最后一首歌曲
            currentPosition=
            musics.size()-1;
        }
        play();
    }

    /**
     * 实现下一首歌曲的播放
     */
    private void next() {
        //当正在播放的歌曲不是最后一首的时候
        //下一首歌曲是当前歌曲下标减一
        if(currentPosition<musics.size()-1) {
            currentPosition++;
        }else{
            //如果已经是最后一首，下一首歌曲是第一首。
            currentPosition=0;
        }
        play();
    }

    //实现音乐的暂停
    private void pause() {
        //停止唱片的转动
        diskView.stopRotation();

        isPlay=false;
        //播放按钮的图片设置为播放
        imageButton_Pause.setImageResource(R.drawable.play);
        //向服务发送一个暂停的广播

        Intent intent=new Intent(IURL.PUASEMUSIC_ACTION);
        //暂停时把要暂停的音乐的路径发给服务
        String musicPath=IURL.ROOT+music.getMusicpath();
        intent.putExtra("musicpath",musicPath);
        sendBroadcast(intent);

    }

    private  class ProgressReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,
                              Intent intent) {
                String action=intent.getAction();
                if(IURL.PLAYNEXT_ACTION.equals(action)){
                    //要播放下一首歌曲
                    next();
                }else if(IURL.UPDATEPROGRESS_ACTION.equals(action)){
                    //获得服务传过的进度更新的数据
                    int cp=intent.getIntExtra("cp",0);
                    int duration=intent.getIntExtra("duration",0);
                    //计算进度条应用更新的进度
                    int progress=cp*100/duration;
                    seekBar.setProgress(progress);

                    textView_Current.setText(new SimpleDateFormat("mm:ss").format(cp));
                    textView_Duration.setText(new SimpleDateFormat("mm:ss").format(duration));
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
