package com.tarena.karen.youlu03.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pjy on 2017/5/17.
 */

public abstract class MyBaseAdapter<T>
        extends BaseAdapter {

    protected LayoutInflater layoutInflater=null;
    protected Context context;

    public MyBaseAdapter(Context context){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }

    private List<T> datas = new ArrayList<T>();

    /**
     * @param list    添加的数据
     * @param isClear 是否清空原有数据
     */
    public void addDatas(
            List<T> list,
            boolean isClear) {
        if (isClear) {
            datas.clear();
        }
        if (list != null) {
            datas.addAll(list);
            //通知绑定适配器的UI进行数据更新
            notifyDataSetChanged();
        }
    }
    //删除所有的数据
    public void removeDatas(){
        datas.clear();
        notifyDataSetChanged();
    }
    //删除指定数据
    public void removeDatas(T t){
        datas.remove(t);
        notifyDataSetChanged();
    }

    public List<T> getDatas(){return datas;}

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public abstract View getView(
            int i,
            View view,
            ViewGroup viewGroup);
}


