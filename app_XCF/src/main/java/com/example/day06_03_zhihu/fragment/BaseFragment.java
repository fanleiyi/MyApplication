package com.example.day06_03_zhihu.fragment;


import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.day06_03_zhihu.R;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * 基础的Fragment父类
 * actionbar初始化方法
 * 其它控件的初始化方法
 */
public abstract class BaseFragment extends Fragment {
    public LinearLayout actionbar;//标题布局
    public View contentView=null;//内容视图
    /**
     *
     * @param leftId 左边图片资源Id
     * @param title 中间的文本内容
     * @param rightId 右边图片资源Id
     */
    public void initialActionbar(
            int leftId,
            String title,
            int rightId){
        if(actionbar==null){
            return;
        }
        ImageView imageView_Left= (ImageView)
                actionbar.findViewById(
                        R.id.image_action_left);
        EditText edit_seek=
                (EditText) actionbar.findViewById(
                        R.id.edit_seek);
        ImageView imageView_Right= (ImageView)
                actionbar.findViewById(
                        R.id.image_action_right);
        if(leftId==-1){
            //资源id如果为-1说明该控件不显示
            imageView_Left.setVisibility(View.INVISIBLE);
        }else{
            //说明要显示一个图片
            imageView_Left.setVisibility(View.VISIBLE);
            imageView_Left.setImageResource(leftId);
        }
        if(TextUtils.isEmpty(title)){
            edit_seek.setVisibility(View.INVISIBLE);
        }else{
            edit_seek.setVisibility(View.VISIBLE);
            edit_seek.setHint(title);
        }
        if(rightId==-1){
            imageView_Right.setVisibility(View.INVISIBLE);
        }else {
            imageView_Right.setVisibility(View.VISIBLE);
            imageView_Right.setImageResource(rightId);
        }
    }
    public  abstract void initialUI();
}
