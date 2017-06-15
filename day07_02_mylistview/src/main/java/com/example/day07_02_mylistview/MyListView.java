package com.example.day07_02_mylistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by tarena on 2017/4/24.
 */

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    /**用于测量View的高度宽度（view的边界）*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE>>2,// 高度的最大值
                MeasureSpec.AT_MOST);// 高度可动态扩展
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
