package com.example.day08_02_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tarena on 2017/4/25.
 */

public class PathView extends View {
    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.设置画笔对象
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        // 2.绘制path图形
        Path path = new Path();
        Path path1 = new Path();
        // 3.设置path对象起始点
        path.moveTo(50,100);
        path.lineTo(50,1000);
        path.lineTo(1000,1000);
        path1.moveTo(50,1000);
        path1.lineTo(200,950);
        path1.lineTo(300,750);
        path1.lineTo(350,850);
        path1.lineTo(400,900);
        path1.lineTo(500,800);
        path1.lineTo(550,700);
        path1.lineTo(700,780);
        path1.lineTo(750,730);
        path1.lineTo(800,650);
        path1.lineTo(900,630);
       // path.lineTo(200,200);
       // path.lineTo(260,300);
        //path.close();// 将图形连接起点终点形成闭环
        canvas.drawPath(path,paint);
        canvas.drawPath(path1,paint);
        //绘制一个文本
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        canvas.drawText("随便画画！",50,1100,paint);

    }
}
