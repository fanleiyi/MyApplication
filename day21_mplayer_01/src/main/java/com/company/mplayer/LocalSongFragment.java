package com.company.mplayer;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.company.mplayer.adapter.SimpleSongAdapter;
import com.company.mplayer.entity.Song;
import com.company.mplayer.service.SongService;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocalSongFragment extends Fragment implements SimpleSongAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private List<Song> songs;
    private SimpleSongAdapter mSongadapter;
    public LocalSongFragment() {
       songs=new ArrayList<>();
    }
    private Context activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_local_song, container, false);
        //1.初始化RecyclerView(每个item要实现音乐名称，歌手)
        //1.1获得RecyclerView对象
        //1.2构建适配器对象(SongAdapter)
        //1.3关联SongAdapter(继承RecyclerView.Adapter)
        //1.4添加监听器对象(例如点击时可以播放音乐)
        //1.4.1 在适配器中编写一个监听接口OnItemClickListener
        //1.4.2 在适配器的onCreateViewHolder方法中为itemview添加点击事件
        //1.4.2.1 获得RecyclerView (重写适配器的onAttachedToRecyclerView)
        //1.4.2.2 获得item在recyclerview中的位置
        //1.4.3 在item点击的时回调我们自己接口的事件处理方法(例如onItemClick)
        setRecyclerView(view);
        //2.初始化SwipeRefreshLayout
        //2.1获得SwipeRefreshLayout对象
        //2.2添加监听对象
        setSwipeRefreshLayout(view);
        //3.借助CusorLoader加载媒体库音乐 :参考onStart方法
        //3.1权限(读取sdcard的权限)
        //3.2初始化cursorloader
        //3.3加载完成刷新RecyclerView
        return view;
    }
    /**Fragment对象启动时执行*/
    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(1,null,this);
    }
    /**初始化SwipeRefreshLayout*/
    private void setSwipeRefreshLayout(View v){
        final SwipeRefreshLayout layout= (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayoutId);
        layout.setColorSchemeResources(android.R.color.holo_green_dark,android.R.color.holo_green_light,android.R.color.holo_blue_light);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("TAG","onRefresh");
                songs.clear();//先清空
                getLoaderManager().
                        restartLoader(1,null,LocalSongFragment.this);
                layout.setRefreshing(false);
            }
        });
    }
    /**初始化RecyclerView*/
    private void setRecyclerView(View view){
        //1获得RecyclerView对象
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        //2.设置LayoutManager对象及item动画
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());//动画
        //3.构建适配器对象(SongAdapter)
        mSongadapter = new SimpleSongAdapter(songs, R.layout.card_song_item_01);
        //3关联SongAdapter(继承RecyclerView.Adapter)
        recyclerView.setAdapter(mSongadapter);
        //4添加监听器对象(例如点击时可以播放音乐)
        mSongadapter.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(RecyclerView parent, View view, int position) {
        Song song=songs.get(position);
        //Toast.makeText(getActivity(),song.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(), SongService.class);
        intent.putExtra("actionKey",SongService.PLAY);
        intent.putExtra("dataKey",song.getData());
        getActivity().startService(intent);
    }
    /**Loader初始化时执行*/
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id==1){
            return new CursorLoader(getActivity(),
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null,null,null,
                    MediaStore.Audio.Media.DATE_ADDED+" desc ");
        }
        return null;
    }
    /**Loader加载完成时执行*/
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
           if(cursor==null)return;
           while(cursor.moveToNext()){//移动指针取数据
               songs.add(new Song(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
                       cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                       cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                       cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))));
           }
           //刷新
           mSongadapter.notifyDataSetChanged();
           //cursor.close();
    }
    /**loader销毁时执行*/
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
           songs.clear();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent target=new Intent(getActivity(),SongService.class);
        getActivity().stopService(target);
    }
}
