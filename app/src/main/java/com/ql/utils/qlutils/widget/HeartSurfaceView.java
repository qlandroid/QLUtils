package com.ql.utils.qlutils.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by android on 2017/11/17.
 */

public class HeartSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    // 子线程标志位
    private boolean isDrawing;
    private Path path;
    private Paint paint;
    private Rect rect;

    public HeartSurfaceView(Context context) {
        super(context);
        init();
    }

    public HeartSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {//创建
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {//改变

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {//销毁
        isDrawing = false;
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);

        path = new Path();
        paint = new Paint();

        angle = 10;

        rect = new Rect(0, 0, 200, 200);

    }

    @Override
    public void run() {
        while (isDrawing) {
            toDrawing();
        }
    }

    private static final String TAG = "HartSurfaceView";
    float angle = 10;

    int refreshIndex;
    int width = 10;
    private Point lastPoint;
    private boolean startReadAngle = true;
    private int mReadCount = 5;
    private Point mFirstPoint;
    private boolean drawHeart = true;
    private float yScale = 20f, xScale = 19.5f;

    private boolean drawing;

    private void toDrawing() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.YELLOW);
            offsetX = getMeasuredWidth() / 2;
            offsetY = getMeasuredHeight() / 2 - 55;
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(width);
            if (isDrawing) {
                int readPathCount = 0;
                while (drawHeart && readPathCount <= mReadCount) {
                    Point p = getHeartPoint(angle);
                    if (lastPoint == null) {
                        path.moveTo(p.x, p.y);
                        lastPoint = p;
                        mFirstPoint = p;
                    } else {
                        if (mFirstPoint.x == p.x && mFirstPoint.y == p.y) {
                            continue;
                        }
                        path.lineTo(p.x, p.y);
                    }
                    angle = angle + 0.01f;
                    readPathCount++;
                }
            }


            //mCanvas.drawPoint(p.x, p.y, paint);
            mCanvas.drawPath(path, paint);
            paint.setStyle(Paint.Style.FILL);

            mCanvas.drawRect(rect, paint);
            paint.setTextSize(20);
            paint.setStrokeWidth(5);
            paint.setColor(Color.BLUE);
            mCanvas.drawText(String.valueOf(angle), 50, 50, paint);


            refreshIndex++;
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    public void setIsStartAngle(boolean isStart) {
        startReadAngle = isStart;
    }

    public void setStrokeWidth(int width) {
        this.width = width;
    }

    public int getStrokeWidth() {
        return width;
    }

    int offsetX, offsetY;

    public void setyScale(float y) {
        yScale = y;
        lastPoint = null;
    }

    public void setxScale(float x) {
        xScale = x;
    }

    public Point getHeartPoint(float angle) {
        float t = (float) (angle / Math.PI);
        float x = (float) (xScale * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-yScale * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));
        return new Point(offsetX + (int) x, offsetY + (int) y);
    }

    public void setDrawing(boolean drawing) {
        this.drawing = drawing;
    }

    public void reset() {
        angle = 0;
        path = new Path();
        lastPoint = null;
    }
}
