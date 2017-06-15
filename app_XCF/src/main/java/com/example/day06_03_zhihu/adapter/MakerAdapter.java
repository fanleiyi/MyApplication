package com.example.day06_03_zhihu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.day06_03_zhihu.R;
import com.example.day06_03_zhihu.entity.Community;
import com.example.day06_03_zhihu.entity.Maker;
import com.example.day06_03_zhihu.manager.ImageManager;

/**
 * Created by tarena on 2017/5/25.
 */

public class MakerAdapter extends MyBaseAdapter<Maker> {
    public MakerAdapter(Context context) {
        super(context);
    }


    class ViewHolder{
        ImageView imageView_png;
        ImageView imageView_photo;
        TextView textView_name;
        TextView textView_body;
        TextView textView_date;
        TextView textView_title;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Maker maker =getItem(i);
        ViewHolder holder=null;
        if(view==null) {
                view = layoutInflater.inflate(R.layout.maker_item_01, null);
                holder = new ViewHolder();
                holder.imageView_photo = (ImageView) view.findViewById(R.id.image_item1_photo);
                holder.imageView_png = (ImageView) view.findViewById(R.id.image_item1_png);
                holder.textView_name = (TextView) view.findViewById(R.id.text_item1_name);
                holder.textView_body = (TextView) view.findViewById(R.id.text_item1_body);
                holder.textView_date = (TextView) view.findViewById(R.id.text_item1_date);
                holder.textView_title = (TextView) view.findViewById(R.id.text_item1_title);
                view.setTag(holder);

        }else{
            holder= (ViewHolder) view.getTag();
        }

            holder.imageView_png.setImageResource(maker.getPngId());
            if (maker.getPhotoId()>0) {
                Bitmap photo = ImageManager.formatBitmapByResources(context,maker.getPhotoId());
                holder.imageView_photo.setImageBitmap(photo);
            }
            holder.textView_name.setText(maker.getName());
            holder.textView_date.setText(maker.getDate());
            holder.textView_title.setText(maker.getTitle());
            holder.textView_body.setText(maker.getBody());

        return view;
    }
}
