package com.ql.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by android on 2017/11/6.
 */

public class TagGroupView extends FrameLayout implements View.OnClickListener {

    private List<List<int[]>> childMargin = new ArrayList<>();
    private Map<Integer, Integer> childLineMaxHeight = new HashMap<>();

    /**
     * 每行之间的间隔 距离
     */
    private int lineDivHeight = 50;
    private int rowDivWidth = 0;
    private Paint mPaint;

    public TagGroupView(Context context) {
        this(context, null, 0);
    }

    public TagGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagGroupView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.TagGroupView_divHeight) {
                lineDivHeight = typedArray.getDimensionPixelSize(index, 20);
            } else if (index == R.styleable.TagGroupView_rowWidth) {
                rowDivWidth = typedArray.getDimensionPixelSize(index, 20);
            }
        }
        typedArray.recycle();

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        setChildLocation();
    }

    private void setChildLocation() {
        int childCount = getChildCount();
        int rowsIndex = 0;
        int lineIndex = 0;
        for (int i = 0; i < childCount; i++) {
            List<int[]> lineView = childMargin.get(rowsIndex);

            if (lineIndex > lineView.size() - 1) {
                lineIndex = 0;
                rowsIndex++;
                lineView = childMargin.get(rowsIndex);
            }
            int[] ints = lineView.get(lineIndex);


            View childAt = getChildAt(i);
            childAt.setClickable(true);
            childAt.setOnClickListener(this);
            childAt.layout(ints[0], ints[1], ints[2], ints[3]);
            lineIndex++;
        }
    }

    private static final String TAG = "TagGroupView";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int maxLine = childLineMaxHeight.size();
        int top = getPaddingTop();
        Log.i(TAG, "onDraw: " + maxLine);
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(Color.RED);
        }
        for (int i = 0; i < maxLine - 1; i++) {
            Integer lineHeight = childLineMaxHeight.get(i);
            int divTop = top + lineHeight;
            int startX = divTop;
            int startY = getPaddingTop();
            int endY = startY + lineDivHeight;
            canvas.drawRect(startX, startY, startX, endY, mPaint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        saveChildLocation(widthMeasureSpec, heightMeasureSpec);

    }

    private void saveChildLocation(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        childMargin.clear();
        childLineMaxHeight.clear();
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int readyWidthSize = sizeWidth;

        int lineMaxWidth = 0;
        int totalHeight = 0;
        int top = getPaddingTop();

        int left = getPaddingLeft();
        int line = 0;

        int childCount = getChildCount();
        ArrayList<int[]> lineList = null;
        for (int i = 0; i < childCount; i++) {

            if (lineList == null) {
                lineList = new ArrayList<>();
            }

            View childView = getChildAt(i);
            LayoutParams childParams = (LayoutParams) childView.getLayoutParams();

            int width = childParams.rightMargin + childView.getMeasuredWidth() + childParams.leftMargin;

            int height = childParams.topMargin + childView.getMeasuredHeight() + childParams.bottomMargin;

            int right = getPaddingRight();


            int lineWidth = left + width + right + getPaddingRight();

            int viewLeft;
            int viewTop;
            int viewRight;
            int viewBottom;
            /*
             * 每行的第一个元素不需要进行判断添加 列之间的宽度，进行比较是否满足 条件 
             */
            if (lineWidth > readyWidthSize) {
                lineMaxWidth = lineMaxWidth > left ? lineMaxWidth : (left - rowDivWidth + getPaddingRight());
                //开始换行
                left = getPaddingLeft();
                top = top + childLineMaxHeight.get(line) + lineDivHeight;
                line++;
                childMargin.add(lineList);
                lineList = new ArrayList<>();
            }
            viewLeft = left + childParams.leftMargin;
            viewTop = top + childParams.topMargin;
            viewRight = viewLeft + childView.getMeasuredWidth();
            viewBottom = viewTop + childView.getMeasuredHeight();
            checkLineMaxHeight(line, height);


            int[] margin = {viewLeft, viewTop, viewRight, viewBottom};
            lineList.add(margin);
            left = left + width + rowDivWidth;


            if (i == childCount - 1) {
                /*
                 最后一次计算总体高度 内上边距 + 所有 view 的高度；
                 */
                totalHeight = top + childLineMaxHeight.get(line);
                lineMaxWidth = lineMaxWidth > left ? lineMaxWidth : (left - rowDivWidth + getPaddingRight());
                childMargin.add(lineList);
            }
        }
        /*
         总高度为 上边距 + 下边距 + 所有行 最高高度
         */
        totalHeight = totalHeight + getPaddingBottom();
        //setChildrenBottomMargin();
        setChildMarginTop();
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
                : lineMaxWidth, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
                : totalHeight);
    }

    private void setChildMarginTop() {

    }


    private void setChildrenBottomMargin() {
        for (int i = 0; i < childMargin.size(); i++) {
            int lineMaxHeight = childLineMaxHeight.get(i);
            List<int[]> childLine = childMargin.get(i);
            for (int[] ints : childLine) {
                ints[3] = lineMaxHeight;
            }
        }

    }

    private void checkLineMaxHeight(int line, int height) {
        Integer maxHeightI = childLineMaxHeight.get(line);
        int maxHeight = 0;
        if (maxHeightI == null) {
            maxHeight = -1;
        } else {
            maxHeight = maxHeightI;
        }
        if (maxHeight < height) {
            childLineMaxHeight.put(line, height);
        }
    }

    @Override
    public void onClick(View v) {
        v.setSelected(!v.isSelected());

    }




}
