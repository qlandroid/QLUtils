package com.ql.utils.qlutils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 创建时间:2017/12/13
 * 描述:
 *
 * @author ql
 */

public class MyInputEditText extends EditText {
    Paint mPaint;

    int lineWidth;

    public MyInputEditText(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public MyInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    public MyInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int measuredHeight = getMeasuredHeight();
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(0, measuredHeight, getMeasuredWidth(), measuredHeight, mPaint);

        mPaint.setColor(Color.RED);
        int centerX = getMeasuredWidth() / 2;
        int startLineX = centerX - lineWidth / 2;
        int endLineX = startLineX + lineWidth;
        canvas.drawLine(startLineX,measuredHeight,endLineX,measuredHeight,mPaint);
    }

    private void changeLineWidth(){
    }
}
