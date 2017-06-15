package com.tarena.karen.youlu03.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarena.karen.youlu03.R;
import com.tarena.karen.youlu03.entity.Conversation;
import com.tarena.karen.youlu03.manager.ContactsManager;
import com.tarena.karen.youlu03.manager.ImageManager;

/**
 * Created by pjy on 2017/5/23.
 */

public  class ConversationAdapter
        extends MyBaseAdapter<Conversation> {
    public ConversationAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null) {
            view = layoutInflater.
                    inflate(R.layout.
                            inflate_conversation_item_layout, null);
            holder = new ViewHolder();
            holder.imageView_Header = (ImageView) view.findViewById(
                    R.id.imageView_Conversation_Header);
            holder.imageView_Warning = (ImageView) view.findViewById(
                    R.id.imageView_Conversation_Warning);
            holder.imageView_Read = (ImageView) view.findViewById(
                    R.id.imageView_Conversation_Read);

            holder.textView_Body = (TextView) view.findViewById(
                    R.id.textView_Conversation_Body);
            holder.textView_Name = (TextView) view.findViewById(
                    R.id.textView_Conversation_Name);
            holder.textView_Date = (TextView) view.findViewById(
                    R.id.textView_Conversation_Date);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        //获得要适配的数据对象
        Conversation conversation=getItem(i);
        String name=conversation.getName();
        if(TextUtils.isEmpty(name)){
            holder.imageView_Warning.setVisibility(View.VISIBLE);
            holder.textView_Name.setTextColor(Color.RED);
            holder.textView_Name.setText(conversation.getAddress());
        }else{
            holder.imageView_Warning.setVisibility(View.INVISIBLE);
            holder.textView_Name.setTextColor(Color.BLACK);
            holder.textView_Name.setText(name);
        }
        String body=conversation.getBody();
        holder.textView_Body.setText(body);
        String date=conversation.getDateStr();
        holder.textView_Date.setText(date);

        int photoid=conversation.getPhotoId();
        //获得头像
        Bitmap header= ContactsManager.
                getPhotoByPhotoId(context,photoid);
        header= ImageManager.
                formatBitmap(context,header);
        holder.imageView_Header.setImageBitmap(header);

        //获得会话读取的状态
        int read=conversation.getRead();
        if(read==0){
            //未读的
            holder.imageView_Read.setVisibility(View.VISIBLE);
        }else if(read==1){
            holder.imageView_Read.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    private class ViewHolder{
        ImageView imageView_Header;
        ImageView imageView_Read;
        ImageView imageView_Warning;

        TextView textView_Name;
        TextView textView_Body;
        TextView textView_Date;
    }
}
