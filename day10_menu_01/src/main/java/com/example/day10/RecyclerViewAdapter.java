package com.example.day10;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by pjy on 2017/4/21.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.InnerViewHolder> implements View.OnClickListener{

    private List<News> mObjects;
    public RecyclerViewAdapter(List<News> objects){
        mObjects=objects;
    }


    static class InnerViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public ImageView mImageView;
        public InnerViewHolder(View itemView) {
            super(itemView);
            mTextView= (TextView) itemView.findViewById(R.id.textId);
            mImageView= (ImageView) itemView.findViewById(R.id.imageId);
        }
    }

    /**构建itemview 并实现与viewholder的绑定*/
    @Override
    public InnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //view对象具体类型由R.layout.list_item_01的根元素决定
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_01,parent,false);
        return new InnerViewHolder(view);
    }
    /**绑定数据*/
    @Override
    public void onBindViewHolder(final InnerViewHolder holder, final int position) {
        News object=mObjects.get(position);//一条新闻
        holder.mImageView.setImageResource(object.getLogo());
        holder.mTextView.setText(object.getTitle());
        holder.mImageView.setTag(position);//数据绑定
        holder.mImageView.setOnClickListener(this);
     /* holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.mImageView.getContext(),position+"", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    @Override
    public void onClick(View v) {
        Integer position=(Integer)v.getTag();
        String title=mObjects.get(position).getTitle();
        Toast.makeText(v.getContext(),title, Toast.LENGTH_SHORT).show();
    }
}
