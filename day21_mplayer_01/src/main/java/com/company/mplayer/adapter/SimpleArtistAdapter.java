package com.company.mplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.mplayer.R;
import com.company.mplayer.entity.Artist;

import java.util.List;

/**
 * Created by pjy on 2017/5/11.
 */

public class SimpleArtistAdapter extends RecyclerView.Adapter<SimpleArtistAdapter.AritistViewHolder> {

    private List<Artist> mObjects;
    private int mResource;
    private RecyclerView mRecyclerView;
    /**item对象监听器(自己写的)*/
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    /**构造方法：作用是初始化属性*/
    public SimpleArtistAdapter(List<Artist> objects, int resouce){
        mObjects=objects;
        mResource=resouce;
    }
    /**Item对象对象的viewholder(持有item中子元素的位置)*/
    static class AritistViewHolder extends RecyclerView.ViewHolder{

        public TextView artistTv;
        public AritistViewHolder(View itemView) {
            super(itemView);
            artistTv= (TextView) itemView.findViewById(R.id.text1);
        }

    }
    /**创建itemview并实现与viewholder对象的绑定*/
    @Override
    public AritistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //初始化itemview对象
        View view=LayoutInflater.from(parent.getContext())
                .inflate(mResource,parent,false);
        //添加item的监听事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//当前应用v-->cardview
                //Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
                int position=mRecyclerView.getChildAdapterPosition(v);
                onItemClickListener.onItemClick(mRecyclerView,v,position);
            }
        });
        //实现viewholder与itemview对象的绑定
        return new AritistViewHolder(view);
    }

    /**将数据设置到holder关联的view上*/
    @Override
    public void onBindViewHolder(SimpleArtistAdapter.AritistViewHolder holder, int position) {
        Artist a=mObjects.get(position);
        holder.artistTv.setText(a.getName()+"("+a.getCount()+")");
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
                                View view, int position);
    }
}
