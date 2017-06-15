package com.company.mplayer;

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

import com.company.mplayer.adapter.SimpleArtistAdapter;
import com.company.mplayer.entity.Artist;

import java.util.ArrayList;
import java.util.List;


public class LocalArtistFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,SimpleArtistAdapter.OnItemClickListener {



    private List<Artist> mArtists=new ArrayList<>();
    private SimpleArtistAdapter mAritstAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_local_artist, container, false);

        //1.初始化RecyclerView
        setRecyclerView(view);
        //2.初始化SwipeRefreshLayout
        setSwipeRefreshLayout(view);
        //3.初始化CursorLoader加载数据
        return view;
    }
    /**初始化SwipeRefreshLayout*/
    private void setSwipeRefreshLayout(View v){
        final SwipeRefreshLayout layout= (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayoutId);
        layout.setColorSchemeResources(android.R.color.holo_green_dark,android.R.color.holo_green_light,android.R.color.holo_blue_light);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("TAG","onRefresh");
                mArtists.clear();//先清空
                getLoaderManager().
                        restartLoader(2,null,LocalArtistFragment.this);
                layout.setRefreshing(false);
            }
        });
    }
    /**初始化RecyclerView*/
    private void setRecyclerView(View view){
        //1获得RecyclerView对象
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        //添加分隔线(因为recycler默认没有分隔线)
        //recyclerView.addItemDecoration(new SimpleItemDecoration());
        //2.设置LayoutManager对象及item动画
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());//动画
        //3.构建适配器对象(SongAdapter)
        mAritstAdapter = new SimpleArtistAdapter(mArtists,
               R.layout.text_artist_item_01);
        //3关联SongAdapter(继承RecyclerView.Adapter)
        recyclerView.setAdapter(mAritstAdapter);
        //4添加监听器对象(例如点击时可以播放音乐)
        mAritstAdapter.setOnItemClickListener(this);
    }
    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(2,null,this);
    }
    /**
     * select artist,count(*) as ct
     * from audio
     * where artist is not null
     * group by artist
     * */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i("TAG","LocalArtistFragment.onCreateLoader");
        if(id==2){
            return  new CursorLoader(getActivity(),
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Audio.Media.ARTIST,
                            "count(*) as ct" },
                    MediaStore.Audio.Media.ARTIST +" is not null ) GROUP By ( "+MediaStore.Audio.Media.ARTIST,null,null);
        }//不做具体要求
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
         Log.i("TAG","onLoadFinished.cursor");
         if(cursor==null)return;
         while(cursor.moveToNext()){
            String artist=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
             int count=cursor.getInt(cursor.getColumnIndex("ct"));
             mArtists.add(new Artist(artist,count));
         }
        Log.i("TAG","mArtists="+mArtists);//将此数据显示在页面上
        mAritstAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mArtists.clear();

    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position) {

    }
}
