package com.example.day08_03_view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void drawBitmap01() {
        ImageView imageView = (ImageView) findViewById(R.id.imageId);
        //1.构建一个位图背景对象(类似一张画纸)
        Bitmap bitmap = Bitmap.createBitmap(200,200,Bitmap.Config.ARGB_8888);
        //2.构建一个画板并关联bimap对象作为背景
        Canvas c = new Canvas(bitmap);
        //3.构建一个画笔对象
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        //4.绘制相关内容(绘制到bitmap背景对象上)
        //4.1绘制一个矩形
        c.drawRect(0,0,200,200,paint);
        paint.setColor(Color.RED);
        //4.2在矩形上绘制一个文本
        String text = "tarena";
        paint.setTextSize(30);
        Rect rect = new Rect();
        //获得文本的边界，并将边界信息封装到rect对象
        paint.getTextBounds(text,0,text.length(),rect);
        c.drawText(text,100-rect.width()/2,100+rect.height()/2,paint);
        //显示绘制内容
        imageView.setImageBitmap(bitmap);
    }

    private void drawBitmap02() {
        ImageView imageView= (ImageView)
                findViewById(R.id.imageId);
        Bitmap imageContent= BitmapFactory.
                decodeResource(getResources(),
                        R.mipmap.b);
        int width=imageContent.getWidth();
        int height=imageContent.getHeight();
        //1.构建一个位图背景对象(类似一张画纸)

        Bitmap bitmap=Bitmap.createBitmap(
                width,
                height,
                Bitmap.Config.ARGB_8888);
        //2.构建一个画板并关联bimap对象作为背景
        Canvas c=new Canvas(bitmap);
        //3.构建一个画笔对象
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        // paint.setColor(Color.YELLOW);
        //4.绘制相关内容(绘制到bitmap背景对象上)
        //4.1绘制圆形
        c.drawCircle(width/2,height/2,100,paint);
        //4.2绘制图片Bitmap
        //设置画笔在绘制内容时的一种相交模式
        paint.setXfermode(
                new PorterDuffXfermode(
                        PorterDuff.Mode.SRC_IN));//留下相交部分，去除非相交部分


        c.drawBitmap(imageContent,0,0,paint);

        //绘制一个空心圆，作为圆形头像的圆边。
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        c.drawCircle(width/2,height/2,100,paint);


        //显示绘制内容
        imageView.setImageBitmap(bitmap);
    }


    }


