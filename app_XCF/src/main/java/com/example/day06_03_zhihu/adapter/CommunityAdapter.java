package com.example.day06_03_zhihu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.day06_03_zhihu.R;
import com.example.day06_03_zhihu.entity.Community;
import com.example.day06_03_zhihu.entity.XCF;
import com.example.day06_03_zhihu.manager.ImageManager;

/**
 * Created by tarena on 2017/5/25.
 */

public class CommunityAdapter extends MyBaseAdapter<Community> {
    public CommunityAdapter(Context context) {
        super(context);
    }

    class ViewHolder{
        ImageView imageView_photo;
        TextView textView_name;
        TextView textView_body;
        TextView textView_date;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder=null;
        if(view==null) {
            view = layoutInflater.inflate(R.layout.community_item_01, null);
            holder = new ViewHolder();
            holder.imageView_photo = (ImageView) view.findViewById(R.id.image_comm_photo);
            holder.textView_name = (TextView) view.findViewById(R.id.text_comm_name);
            holder.textView_body = (TextView) view.findViewById(R.id.text_comm_body);
            holder.textView_date = (TextView) view.findViewById(R.id.text_comm_date);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        Community community =getItem(i);
        Bitmap photo = ImageManager.formatBitmapByResources(context,community.getPhotoId());
        holder.imageView_photo.setImageBitmap(photo);
        holder.textView_name.setText(community.getName());
        holder.textView_body.setText(community.getBody());
        holder.textView_date.setText(community.getDate());

        return view;
    }
}
