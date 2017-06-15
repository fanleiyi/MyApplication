package com.tarena.karen.youlu03.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.CallLog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarena.karen.youlu03.R;
import com.tarena.karen.youlu03.entity.Calllog;
import com.tarena.karen.youlu03.manager.ContactsManager;
import com.tarena.karen.youlu03.manager.ImageManager;

/**
 * Created by pjy on 2017/5/22.
 */

public  class CalllogAdapter
        extends MyBaseAdapter<Calllog> {
    public CalllogAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i,
                View view,
                ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            view=layoutInflater.inflate(R.layout.inflate_calllog_item_layout,null);
            holder=new ViewHolder();
            holder.imageView_Header=
                    (ImageView) view.
                    findViewById(
                    R.id.imageView_Calllog_Header);
            holder.imageView_OutGoing= (ImageView) view.
                    findViewById(
                    R.id.imageView_Calllog_Going);
            holder.imageView_Warning= (ImageView) view.
                    findViewById(
                    R.id.imageView_Calllog_Warning);

            holder.textView_Name= (TextView) view.
                    findViewById(
                    R.id.textView_Calllog_Name);
            holder.textView_Number= (TextView) view.
                    findViewById(
                    R.id.textView_Calllog_Number);
            holder.textView_Date= (TextView) view.
                    findViewById(
                    R.id.textView_Calllog_Date);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        //获得适配的数据项
        Calllog calllog=getItem(i);

        String name=calllog.getName();
        if(TextUtils.isEmpty(name)){
            Log.i("TAG:null",calllog.getName()+"");
            //如果为空说明通话的对象是一个陌生来电
            holder.textView_Name.setText("未知号码");
            holder.textView_Name.setTextColor(Color.RED);
            //未知号码的来电显示警告的小图标
            holder.imageView_Warning.
            setVisibility(View.VISIBLE);
        }else{
            Log.i("TAG:is not null",calllog.getName());
            //不是陌生来电
            holder.textView_Name.
                    setText(name);
            holder.textView_Name.
                    setTextColor(Color.BLACK);
            //警告的图标隐藏
            holder.imageView_Warning.
                    setVisibility(View.INVISIBLE);
        }
        holder.textView_Number.setText(calllog.getPhone());
        holder.textView_Date.setText(calllog.getDateStr());
        //设置头像
        int photoId=calllog.getPhotoId();
        //获得头像
        Bitmap photo= ContactsManager.
                getPhotoByPhotoId(
                context,photoId);
        //格式化头像
        photo= ImageManager.
                formatBitmap(context,photo);
        holder.imageView_Header.
                setImageBitmap(photo);
        //电话类型处理
        //如果是打出的电话显示小图标
        if(calllog.getType()==
                CallLog.Calls.OUTGOING_TYPE){
            holder.imageView_OutGoing.
                    setVisibility(View.VISIBLE);
        }else{
            holder.imageView_OutGoing.
                    setVisibility(View.INVISIBLE);
        }

        return view;
    }

    private class ViewHolder{
        ImageView imageView_Header;
        ImageView imageView_Warning;
        ImageView imageView_OutGoing;

        TextView textView_Name;
        TextView textView_Number;
        TextView textView_Date;
    }
}
