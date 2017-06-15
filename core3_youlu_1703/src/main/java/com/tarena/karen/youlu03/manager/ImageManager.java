package com.tarena.karen.youlu03.manager;

/**
 * Created by pjy on 2017/5/18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.TypedValue;

/**
 * 实现里圆形头像的处理
 */
public class ImageManager {
    /**
     *
     * @param context
     * @param bitmap 需要进行格式化的头像
     * @return
     */
    public static Bitmap formatBitmap(
            Context context,Bitmap bitmap){
        //获得原始头像的宽度和高度
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();

        //创建一个画布的背景图片
        Bitmap backBitmap=Bitmap.createBitmap(
                width,height,
                Bitmap.Config.ARGB_8888);
        //创建一个画布
        Canvas canvas=new
                Canvas(backBitmap);

        //创建一个画笔
        Paint paint=new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置画笔的颜色
        paint.setColor(Color.BLACK);

        //计算圆的半径
        int radius=Math.min(width,height)/2;
        canvas.drawCircle(
                width/2,
                height/2,
                radius,paint);
        //设置画笔的模式
        paint.setXfermode(
                new PorterDuffXfermode(
                PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,0,0,paint);

        //设置画笔的颜色
        paint.setColor(Color.WHITE);
        //设置画出来的圆的样式
        paint.setStyle(Paint.Style.STROKE);

        //计算一下圆环的宽度
        float strokeWidth=
                TypedValue.
                applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,2,context.getResources().getDisplayMetrics());
        //设置圆环边线的粗细
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(width/2,height/2,
                radius-strokeWidth/2,paint);

        return backBitmap;
    }

}
