package com.example.day14_02_music;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarena on 2017/5/4.
 */

public class SimpleSongAsyncLoader extends AsyncTask<Void,Void,List<Song>>{
    private WeakReference<View> weakReference;
    private Context mContext;
    public SimpleSongAsyncLoader(Context ctx, View view) {
        weakReference=new WeakReference<View>(view);
        mContext=ctx;
    }
    @Override
    protected List<Song> doInBackground(Void... params) {
        // 通过contentprovider访问媒体库中的音乐信息
        View view=weakReference.get();
        if (view==null)return null;
        Cursor cursor= mContext.getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        new String[]{//projection,null代表所有列
                                MediaStore.Audio.Media.TITLE,
                                MediaStore.Audio.Media.ARTIST,
                                MediaStore.Audio.Media.DATA},
                                MediaStore.Audio.Media.IS_MUSIC+"=?",//selection表示查询条件
                                new String[]{"1"},//selectionArgs 表示selection表示查询条件中？的值
                                MediaStore.Audio.Media.DATE_ADDED+" desc ");//order by ,其中desc表示降序
        List<Song> mSongs = new ArrayList<>();
        while (cursor.moveToNext()) {
            mSongs.add(new Song(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))));
        }
        cursor.close();
        return mSongs;
    }

    @Override
    protected void onPostExecute(List<Song> songs) {
        super.onPostExecute(songs);
        // 刷新yem
        SwipeRefreshLayout layout = (SwipeRefreshLayout) weakReference.get();
        if (layout!=null) {
            RecyclerView recyclerView= (RecyclerView) layout.findViewById(R.id.recycleId);
            SimpleSongAdapter adapter= (SimpleSongAdapter) recyclerView.getAdapter();
            adapter.addAll(songs);
            adapter.notifyDataSetChanged();
            layout.setRefreshing(false);
        }
    }
}
