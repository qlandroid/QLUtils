package com.ql.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Created by android on 2017/11/10.
 */

public class AutoTextView extends TextView {
    private Paint mPaint;
    int bgColor = Color.BLUE;
    private String text = "测试文字单行无线循环";
    private int textSize = 16;//sp
    int x;
    int y;


    public AutoTextView(Context context) {
        this(context, null);
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoTextView, defStyleAttr, 0);

        int indexCount = typedArray.getIndexCount();

        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            if (R.styleable.AutoTextView_bgColor == index) {
                bgColor = typedArray.getColor(index, Color.DKGRAY);
                continue;
            }
            if (R.styleable.AutoTextView_tSize == index) {
                textSize = typedArray.getDimensionPixelSize(index, textSize);
                continue;
            }
        }

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMsize = 0;
        int heightMsize = 0;

        if (heightMode == MeasureSpec.EXACTLY) {
            heightMsize = heightSize;
        } else {
            float tSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, getResources().getDisplayMetrics());
            heightMsize = (int) (tSize + getPaddingBottom() + getPaddingTop());
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            widthMsize = widthSize;
        } else {
            float width = mPaint.measureText(text);
            widthMsize = (int) (width + getPaddingRight() + getPaddingLeft());
        }

        setMeasuredDimension(widthMsize, heightMsize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}