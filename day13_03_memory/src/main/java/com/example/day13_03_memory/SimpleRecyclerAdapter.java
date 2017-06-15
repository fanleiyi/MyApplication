package com.example.day13_03_memory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tarena on 2017/5/3.
 */

public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.SimpleViewHolder>{

    private LayoutInflater layoutInflater;
    private List<String> mObjects;
    public SimpleRecyclerAdapter(Context context, List<String> objects){
        layoutInflater = LayoutInflater.from(context);
        mObjects=objects;

    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder{
        private View itemView;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
        }
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        if (holder.itemView!=null && holder.itemView instanceof TextView) {
            ((TextView) holder.itemView).setText(mObjects.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return mObjects!=null?mObjects.size():0;
    }
    public void addAll(List<String> objects) {
        mObjects.addAll(objects);
    }

    public void add(String item){
        mObjects.add(0,item);
    }
    public void addAll(int index,List<String> objects){
        mObjects.addAll(index,objects);
    }
}
