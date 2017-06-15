package com.tarena.karen.youlu03.fragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tarena.karen.youlu03.R;

/**
 * Created by pjy on 2017/5/17.
 */

public abstract class BaseFragment
        extends Fragment {
    //定义一个方法实现对标题三个控件的初始设置

    protected View contentView=null;
    protected LinearLayout actionbar;
    /**
     *
     * @param leftId 左边图片资源ID
     * @param title 中间的文本
     * @param rightId 右边的图片资源ID
     */
    public void initialActionbar(
            int leftId,
            String title,
            int rightId){
        if(actionbar==null){
            return;
        }
        //获得标题栏当中的各个控件
        ImageView imageView_Left=
                (ImageView) actionbar.findViewById(
                        R.id.imageView_ActionBar_Left);
        TextView textView_Title=
                (TextView) actionbar.findViewById(
                        R.id.textView_ActionBar_Title);
        ImageView imageView_Right=
                (ImageView) actionbar.findViewById(
                        R.id.imageView_ActionBar_Right);
        //如果第一个参数小于等0说明左边图不显示
        if(leftId<=0){
            imageView_Left.setVisibility(
                    View.INVISIBLE);
        }else{
            imageView_Left.
                    setVisibility(View.VISIBLE);
            imageView_Left.
                    setImageResource(leftId);
        }
        if(TextUtils.isEmpty(title)){
            textView_Title.
                    setVisibility(View.INVISIBLE);
        }else{
            textView_Title.
                    setVisibility(View.VISIBLE);
            textView_Title.
                    setText(title);
        }
        if(rightId<=0){
            imageView_Right.
                    setVisibility(View.INVISIBLE);
        }else{
            imageView_Right.
                    setVisibility(View.VISIBLE);
            imageView_Right.
                    setImageResource(rightId);
        }
    }
    public abstract void initialUI();
}
