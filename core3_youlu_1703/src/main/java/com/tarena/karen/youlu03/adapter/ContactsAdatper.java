package com.tarena.karen.youlu03.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarena.karen.youlu03.R;
import com.tarena.karen.youlu03.entity.Contact;
import com.tarena.karen.youlu03.manager.ContactsManager;
import com.tarena.karen.youlu03.manager.ImageManager;

/**
 * Created by pjy on 2017/5/17.
 */

public  class ContactsAdatper
        extends MyBaseAdapter<Contact> {
    public ContactsAdatper(Context context) {
        super(context);
    }

    @Override
    public View getView(
            int i,
            View view,
            ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            //如果view为空说明适配的是第一屏的数据
            //在内存缓存中还没有可以重用的Item View 项
            //需要我们根据项布局通过布局解析器去进行解析
            view=layoutInflater.inflate(R.layout.inflate_contact_item_layout,null);
            holder=new ViewHolder();
            holder.imageView_Header= (ImageView)
                    view.findViewById(
                    R.id.imageView_Contact_Header);
            holder.textView_Name= (TextView)
                    view.findViewById(
                    R.id.textView_Contact_Name);
            view.setTag(holder);
        }else{
           holder= (ViewHolder) view.getTag();
        }
        //获得适配器集合中的一个数据对象
        Contact contact=getItem(i);
        if(i==0){
            //说明当适配器的联系人是添加联系人项
            //对它的头像作一个特殊的处理
            holder.imageView_Header.
                    setImageResource(
                    R.drawable.ic_add_contact);
        }else {
            //获得联系人的头像的信息
            Bitmap photo= ContactsManager.
                    getPhotoByPhotoId(
                    context,
                    contact.getPhotoId());
            //对象进行圆形的格式化
            photo= ImageManager.
                    formatBitmap(context,photo);
            holder.imageView_Header.
                    setImageBitmap(photo);
        }
        //获得联系人的姓名的信息
        holder.textView_Name.setText(
                contact.getName());
        return view;
    }

    private class ViewHolder{
        ImageView imageView_Header;
        TextView textView_Name;
    }

}
