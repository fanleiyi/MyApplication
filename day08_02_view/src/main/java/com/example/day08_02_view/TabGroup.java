package com.example.day08_02_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RadioGroup;

/**
 * Created by tarena on 2017/4/25.
 */

public class TabGroup extends RadioGroup {
    public TabGroup(Context context) {
        super(context);
    }

    public TabGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private Paint paint =new Paint(Paint.ANTI_ALIAS_FLAG);
    private int redLineStartX;
    /**当需要重写绘制viewgroup容器的子元素时可以考虑重写此方法*/
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //1.保存原有绘制状态
        canvas.save();
        //2.绘制新的内容
        //2.1绘制一条灰色的线
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        Path path=new Path();
        // 设置高度
        int height=getHeight();
        // 设置宽度
        int width=getWidth();

        path.moveTo(0,height);
        path.lineTo(width,height);
        canvas.drawPath(path,paint);

        //绘制一条红色线
        path=new Path();
        path.moveTo(redLineStartX,height);
        path.lineTo(redLineStartX+width/getChildCount(),height);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(15);
        canvas.drawPath(path,paint);
        //3.恢复原有绘制状态
        canvas.restore();

    }//此方法在调用了invalidate()方法会重新执行

    public void update(int position) {
        // 1.修改红色线起始位置坐标
        redLineStartX= position*(getWidth()/getChildCount());
        // 2.重新绘制
        invalidate();

    }

}


