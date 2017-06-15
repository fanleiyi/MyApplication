package com.example.day14_02_music;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tarena on 2017/5/4.
 */

public class SimpleSongAdapter extends RecyclerView.Adapter<SimpleSongAdapter.SimpleSongViewHolder> {

    static class SimpleSongViewHolder extends RecyclerView.ViewHolder{

        /**用于显示歌曲*/
        public TextView titleTv;
        /**用于显示歌手信息*/
        public TextView artistTv;
        public SimpleSongViewHolder(View itemView) {
            super(itemView);
            titleTv= (TextView) itemView.findViewById(android.R.id.text1);
            artistTv= (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
    private List<Song> mSongs;
    public SimpleSongAdapter(List<Song> songs){
        mSongs=songs;
    }
    @Override
    public SimpleSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2,parent,false);

        return new SimpleSongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleSongViewHolder holder, int position) {

        Song song=mSongs.get(position);

        holder.titleTv.setText(song.getTitle());
        holder.artistTv.setText(song.getArtist());
        //.....

    }

    @Override
    public int getItemCount() {
        return mSongs!=null?mSongs.size():0;
    }

    public void addAll(List<Song> songs){
        mSongs.clear();
        mSongs.addAll(songs);
    }
}