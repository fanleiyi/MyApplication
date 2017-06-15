package com.tarena.karen.youlu03.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarena.karen.youlu03.R;
import com.tarena.karen.youlu03.entity.SMS;
import com.tarena.karen.youlu03.manager.ContactsManager;
import com.tarena.karen.youlu03.manager.ImageManager;

/**
 * Created by pjy on 2017/5/24.
 */

public  class SMSAdapter extends
        MyBaseAdapter<SMS> {
    public static final int LEFT_TYPE=0;
    public static final int RIGHT_TYPE=1;
    public SMSAdapter(Context context) {
        super(context);
    }

    //通过重写该方法实现数据适配时获得不同的布局类型
    @Override
    public int getItemViewType(int position) {
        int type=getItem(position).
                getType();
        return type-1;
    }

    //通过重写该方法实现返回多个布局数目
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        int type=getItemViewType(i);
        if(view==null){
            if(type==LEFT_TYPE){
                //使用左边布局做数据适配
                view=layoutInflater.inflate(R.layout.inflate_smsleft_item_layout,null);
            }else if (type==RIGHT_TYPE){
                //使用右边布局做数据适配
                view=layoutInflater.inflate(R.layout.inflate_smsright_item_layout,null);
            }
            holder=new ViewHolder();
            holder.textView_Date= (TextView)
                    view.findViewById(
                    R.id.textView_SMS_Date);
            holder.imageView_Header= (ImageView)
                    view.findViewById(
                    R.id.imageView_SMS_Header);
            holder.textView_Body= (TextView)
                    view.findViewById(
                    R.id.textView_SMS_Body);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        //获得要适配的数据对象
        SMS sms=getItem(i);
        holder.textView_Date.setText(sms.getDateStr());
        holder.textView_Body.setText(sms.getBody());
        //头像的设置
        //如果是收到的短信
        if(type==LEFT_TYPE){
            int photoId=sms.getPhotoId();
            Bitmap header= ContactsManager.
                    getPhotoByPhotoId(context,photoId);
            //格式化头像
            header= ImageManager.
                    formatBitmap(context,header);
            holder.imageView_Header.setImageBitmap(header);
        }else if(type==RIGHT_TYPE){
            //如果是发出去的短信
            //头像使用默认的
            Bitmap header=BitmapFactory.decodeResource(
                    context.getResources(),
                    R.drawable.ic_contact_selected);
            header=ImageManager.
                    formatBitmap(context,header);
            holder.imageView_Header.setImageBitmap(header);
        }
        return view;
    }

    private class ViewHolder{
        ImageView imageView_Header;
        TextView textView_Date;
        TextView textView_Body;
    }
}
