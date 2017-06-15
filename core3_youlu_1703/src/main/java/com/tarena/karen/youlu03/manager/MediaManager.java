package com.tarena.karen.youlu03.manager;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by pjy on 2017/5/23.
 */

public class MediaManager {
    public static SoundPool soundPool=null;

    /**
     * 控制声音的播放
     * @param context
     * @param resId 要播放的声音的资源ID
     */
    public static void playMusic(
            Context context,
            int resId){
       if(soundPool==null){
           soundPool=new SoundPool(
                   4,
                   AudioManager.STREAM_MUSIC,
                   0);
       }
        //给音效设置一个音乐加载的监听器
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool,
                                       int i, int i1) {
                //该方法回调说明这个加载完毕可以进行播放了
                soundPool.play(i,1.0f,1.0f,1,0,1.0f);
            }
        });
        //往音效池中加载音效
        soundPool.load(context,resId,1);
    }

    public static void release(){
        if(soundPool!=null){
            soundPool.release();
            soundPool=null;
        }
    }
}
