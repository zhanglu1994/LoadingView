package com.example.zhangl.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

public class CircleView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);



    private int color;

    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint.setDither(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int cx = getHeight()/2;
        int cy = getHeight()/2;

        canvas.drawCircle(cx,cy,cx,mPaint);


    }

    public void setColor(int color){
        this.color = color;
        mPaint.setColor(color);
        invalidate();
    }




    public int getColor() {
        return color;
    }
}
