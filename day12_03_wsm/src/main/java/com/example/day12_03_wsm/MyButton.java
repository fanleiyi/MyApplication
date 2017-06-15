package com.example.day12_03_wsm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by tarena on 2017/5/2.
 */
public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private float progress;
    public void setProgress(float progress) {
        this.progress = progress;
    }
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**此方法用于绘制view内容*/
    @Override
    protected void onDraw(Canvas canvas) {
        // 定义左上角坐标
        float left = 0;
        float top = 0;
        // 定义右下角坐标
        float right = getWidth()*progress;
        float bottom = getBottom()-getPaddingBottom();
        paint.setColor(Color.YELLOW);
        // 绘制一个矩形
        canvas.drawRect(left,top,right,bottom,paint);
        super.onDraw(canvas);
    }
}
