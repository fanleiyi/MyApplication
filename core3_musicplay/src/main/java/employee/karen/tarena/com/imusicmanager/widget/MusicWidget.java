package employee.karen.tarena.com.imusicmanager.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import employee.karen.tarena.com.imusicmanager.R;
import employee.karen.tarena.com.imusicmanager.entity.Music;
import employee.karen.tarena.com.imusicmanager.iconstant.IURL;

/**
 * Implementation of App Widget functionality.
 */
public class MusicWidget extends AppWidgetProvider {


    public static List<Music> musics;
    public static int position = 0;

    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        if (musics != null && musics.size() > 0) {
            //更新组件的UI
            RemoteViews remoteViews =
                    new RemoteViews(
                            context.getPackageName(), R.layout.music_widget);
            remoteViews.setViewVisibility(R.id.imageButton_Widget_Play,
                    View.VISIBLE);
            remoteViews.setViewVisibility(R.id.imageButton_Widget_Pause,
                    View.INVISIBLE);
            remoteViews.setViewVisibility(R.id.imageButton_Widget_Next,
                    View.VISIBLE);
            setWidget(context, remoteViews);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        }
    }

    private void setWidget(Context context, RemoteViews remoteViews) {
        Music music = musics.get(position);
        remoteViews.setTextViewText(
                R.id.textView_Widget_Name,
                music.getName());
        remoteViews.setTextViewText(
                R.id.textView_Widget_Singer,
                music.getSinger());

        String musicPath=
                IURL.ROOT+music.getMusicpath();
        //给三个按钮分别注册点击事件

        Intent intentPlay=new Intent(IURL.PLAYMUSIC_ACTION);
        intentPlay.putExtra("musicpath",musicPath);
        PendingIntent pendingPlay=
                PendingIntent.getBroadcast(context,1,
                intentPlay,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.imageButton_Widget_Play,pendingPlay);

        Intent intentPause=new Intent(IURL.PUASEMUSIC_ACTION);
        intentPause.putExtra("musicpath",musicPath);
        PendingIntent pendingPause=
                PendingIntent.getBroadcast(
                context,1,intentPause,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(
                R.id.imageButton_Widget_Pause,pendingPause);

        Intent intentNext=new Intent(IURL.PLAYNEXTWIDGET_ACTION);
        musicPath=IURL.ROOT+musics.get(music.getId()).getMusicpath();
        intentNext.putExtra("musicpath",musicPath);
        PendingIntent pendingNext=
                PendingIntent.getBroadcast(context,1,intentNext,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(
                R.id.imageButton_Widget_Next,pendingNext);
    }

    @Override
    public void onReceive(
            Context context,
            Intent intent) {
        super.onReceive(context, intent);

       RemoteViews views=new RemoteViews(context.getPackageName(),
                R.layout.music_widget);
        AppWidgetManager manager=AppWidgetManager.
                getInstance(context);
        ComponentName componentName=
                new ComponentName(
                context,MusicWidget.class);


        String action = intent.getAction();
        if (IURL.MUSICSLOAD_ACTION.equals(action)) {
            //音乐列表已经加载完成
            //获得加载完成的音乐数据
            musics = (ArrayList<Music>) intent.getSerializableExtra("musics");
            Log.i("TAG:", "音乐加载完成:" + musics.size());
        }else if(IURL.UPDATEPAUSEWIDGET_ACTION.
                equals(action)){
          //服务发来的更新暂停按钮的广播
            Log.i("TAG:","开始播放了,显示暂停");
            views.setViewVisibility(
                    R.id.imageButton_Widget_Play,View.INVISIBLE);
            views.setViewVisibility(R.id.imageButton_Widget_Pause,
                    View.VISIBLE);
            String musipath=intent.getStringExtra("musicpath");

            //遍历歌曲列表，判断要播放的歌曲在整个列表当中的位置
            //更新position从而更新歌曲的名字和演唱者
            for(int i=0;i<musics.size();i++){
               String path=IURL.ROOT+musics.get(i).getMusicpath();
                if(path.equals(musipath)){
                  position=i;
                    //更新组件
                    setWidget(context,views);
                    manager.updateAppWidget(componentName,views);
                    break;
                }
            }
        }else if(IURL.UPDATEPLAYWIDGET_ACTION.equals(action)){
            //隐藏暂停,显示播放
        views.setViewVisibility(R.id.imageButton_Widget_Pause, View.INVISIBLE);
        views.setViewVisibility(R.id.imageButton_Widget_Play,View.VISIBLE);
        manager.updateAppWidget(componentName,views);
        }else if(IURL.PLAYNEXT_ACTION.equals(action)){
            //播放器已经播放完毕一首歌曲，需要播放下一首歌曲了
            Log.i("TAG:","收到播放下一首的广播");
            if(position<musics.size()-1){
                position+=1;
            }else{
                position=musics.size()-1;
            }
            Intent intent_next=new Intent();
            String musicPath=IURL.ROOT+musics.get(position).getMusicpath();
            intent.setAction(IURL.PLAYMUSIC_ACTION);
            intent.putExtra("musicpath",musicPath);
            context.sendBroadcast(intent_next);
        }
    }
}

