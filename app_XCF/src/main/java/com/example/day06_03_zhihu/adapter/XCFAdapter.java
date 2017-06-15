package com.example.day06_03_zhihu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.day06_03_zhihu.R;
import com.example.day06_03_zhihu.entity.XCF;

import org.w3c.dom.Text;

/**
 * Created by tarena on 2017/5/25.
 */

public class XCFAdapter extends MyBaseAdapter<XCF> {
    public XCFAdapter(Context context) {
        super(context);
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder=null;
        if(view==null) {
            view = layoutInflater.inflate(R.layout.xcf_item_01, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.imageView_xcf);
            holder.textView = (TextView) view.findViewById(R.id.textView_xfc_title);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        XCF xcf =getItem(i);
        holder.imageView.setImageResource(xcf.getPrint());
        holder.textView.setText(xcf.getTitle());

        return view;
    }
}
