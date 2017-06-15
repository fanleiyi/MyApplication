package com.example.day19_music;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.day19_music.entity.Song;

import java.util.List;

/**
* Created by pjy on 2017/5/11.
        */

public class SimpleSongAdapter extends RecyclerView.Adapter<SimpleSongAdapter.SongViewHolder> {
    private List<Song> mObjects;
    private int mResource;
    private RecyclerView mRecyclerView;
    /**item对象监听器(自己写的)*/
    private OnItemClickListener onItemClickListener;
    private AnimationDrawable animationDrawable;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    /**构造方法：作用是初始化属性*/
    public SimpleSongAdapter(List<Song> objects, int resouce){
        mObjects=objects;
        mResource=resouce;
    }
    /**Item对象对象的viewholder(持有item中子元素的位置)*/
    static class SongViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTv;
        public TextView artistTv;
        private ImageView imageView;

        public SongViewHolder(View itemView) {
            super(itemView);
            titleTv= (TextView) itemView.findViewById(R.id.titleId);
            artistTv= (TextView) itemView.findViewById(R.id.artistId);
            imageView= (ImageView) itemView.findViewById(R.id.closeId);

        }

    }
    /**创建itemview并实现与viewholder对象的绑定*/
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //初始化itemview对象
        View view=LayoutInflater.from(parent.getContext())
                .inflate(mResource,parent,false);
        //添加item的监听事件
        // recyclerView.setItemAnimator(new DefaultItemAnimator());//动画
        ImageView imageview = (ImageView) view.findViewById(R.id.imageId);
        Log.i("TAG","imageview"+imageview);
       animationDrawable = (AnimationDrawable) imageview.getDrawable();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//当前应用v-->cardview
                //Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
                int position=mRecyclerView.getChildAdapterPosition(v);
                onItemClickListener.onItemClick(mRecyclerView,v,position);
            }
        });
        //实现viewholder与itemview对象的绑定
        return new SongViewHolder(view);
    }

    public void animationstart(){
        animationDrawable.start();
    }

    public void animationstop(){
        animationDrawable.stop();
    }
    /**将数据设置到holder关联的view上*/
    @Override
    public void onBindViewHolder(SimpleSongAdapter.SongViewHolder holder, int position) {
        Song song=mObjects.get(position);

        holder.titleTv.setText(song.getTitle());
        holder.artistTv.setText(song.getArtist());
        //在imageview上绑定一个位置

        holder.imageView.setTag(position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongService.currentPosition =0;
                int position=(Integer) v.getTag();
                mObjects.remove(mObjects.get(position));
                Intent target=new Intent(v.getContext(),SongService.class);
                target.putExtra("actionKey", SongService.PAUSE);
                v.getContext().startService(target);
                LocalSongFragment.item =null;
                notifyDataSetChanged();
                //notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mObjects!=null?mObjects.size():0;
    }

    //关联recyclerview时执行
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView=recyclerView;
    }
    //取消关联recyclerview时执行
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView=null;
    }

    /**定义RecyclerView中itemview的监听器接口*/
    public interface  OnItemClickListener{
        /**点击recyclerview中的item时执行此方法
         * @param  parent 指向recyclerview
         * @param  view 指向itemview
         * @param  position itemview在recyclerview中的位置
         * */
        public void onItemClick(RecyclerView parent,
                                View view,int position);
    }
}
