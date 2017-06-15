package com.example.day08_01_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by pjy on 2017/4/25.
 * 1.自定义view(直接或间接的继承view对象)
 * 2.自定义view的属性
 * a)在res/values目录下新建attrs.xml文件(根元素resources)
 * b)在view对应的布局中为定义的属性赋值
 * c)在view对应的构造方法中获得属性的值
 */

public class CircleIndicator extends View {
    public CircleIndicator(Context context) {
        super(context);
    }

    /**@param attrs 对象封装了view中的所有属性及值*/
    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i("TAG","CircleIndicator(Context context, AttributeSet attrs)");
        //获得attrs属性集中R.styleable.CircleIndicator表中属性的值
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.CircleIndicator);
        //获得R.styleable.CircleIndicator表中具体属性的值
        radius=typedArray.getInt(R.styleable.CircleIndicator_radius,10);
        Log.i("TAG","radius="+radius);
        num=typedArray.getInt(R.styleable.CircleIndicator_num,0);
        //回收typedArray对象
        typedArray.recycle();
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**绘制的空心圆的个数*/
    private int num;
    /**空心圆x轴位置*/
    private float skrokeStartXPos;
    /**实心圆x轴的位置*/
    private float fillStartXPos;
    /**空心圆Y轴坐标位置*/
    private float skrokeStartYPos;
    /**此方法是在页面大小发生变化时执行,例如横竖切换屏幕
     * @param w  最新的view的宽度
     * @param h  最新的view的高度
     * */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //假设两个圆心之间的距离为3个半径
        skrokeStartXPos=fillStartXPos=w/2-3*radius;
        skrokeStartYPos=h/2;
    }

    private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius=50;
    /**此方法是在view的draw方法中执行的调用,
     * 用于绘制view的内容
     * @param canvas  代表一个画板对象
     * */
    @Override
    protected void onDraw(Canvas canvas) {//CircleIndicator
        super.onDraw(canvas);
        //设置画笔样式(线条)
        paint.setStyle(Paint.Style.STROKE);
        //设置画笔颜色
        paint.setColor(Color.BLACK);
        //设置画笔线条粗细
        paint.setStrokeWidth(10);
        //执行绘制动作(绘制三个空心圆)
        for (int i=0;i<num;i++) {
            canvas.drawCircle(skrokeStartXPos+3*radius*i, skrokeStartYPos, radius, paint);
        }
        //绘制一个实心圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(fillStartXPos,skrokeStartYPos,radius-5,paint);
    }
    public void updateFillCirclePos(int position,float offset){
        fillStartXPos=skrokeStartXPos+3*radius*(position+offset);
        //重新绘制(invalidate()方法被调用后会重新执行view的onDraw方法)
        invalidate();
    }
}
