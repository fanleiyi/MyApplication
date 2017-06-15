package com.example.day05;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pjy on 2017/4/20.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.InnerViewHolder> {
    private String[] mObjects;
    private int[] mImages;
    public RecyclerAdapter(String[] objects,int[] images){
        mObjects=objects;
        mImages=images;

    }
    //借助holder对象记录item中view元素的位置
    static class InnerViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public ImageView mImageView;
        public InnerViewHolder(View view) {
            super(view);
            mImageView= (ImageView) view.findViewById(R.id.imageId);
            mTextView= (TextView) view.findViewById(R.id.textViewId);
        }
    }

    /**创建itemview并实现view与viewholder绑定*/
    @Override
    public InnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_02,parent,false);


        return new InnerViewHolder(view);
    }

    /**包饺子：数据绑定*/
    @Override
    public void onBindViewHolder(InnerViewHolder holder, int position) {
        holder.mTextView.setText(mObjects[position]);
        holder.mImageView.setImageResource(mImages[position]);
    }

    @Override
    public int getItemCount() {
        return mObjects.length;
    }
}
