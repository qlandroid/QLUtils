package com.ql.utils.qlutils.move;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.icu.text.BreakIterator;
import android.view.MotionEvent;
import android.view.View;

/**
 * 创建时间:2017/12/11
 * 描述:
 *
 * @author ql
 */

public class ZoomBoxOperate {
    public static final int ZOOM_LOCTION_TOP_LEFT = 0;
    public static final int ZOOM_LOCTION_TOP_RIGHT = 1;
    public static final int ZOOM_LOCTION_BOTTOM_LEFT = 2;
    public static final int ZOOM_LOCTION_BOTTOM_RIGHT = 3;

    private ZoomBox zoomBox;
    private OnZoomListener mOnZoomListener;


    public ZoomBoxOperate(ZoomBox zoomBox, View view) {
        this.zoomBox = zoomBox;

    }

    /**
     * 绘制 当前放大区域的
     *
     * @param canvas
     */
    public void drawZoomButton(Canvas canvas, Paint paint) {

        Module toSmall = zoomBox.getToSmall();
        if (toSmall != null) {
            paint.setColor(toSmall.getColor());
            drawModule(canvas, toSmall, paint);
        }

        Module toBig = zoomBox.getToBig();
        if (toBig != null) {
            if (paint.getColor() != toBig.getColor()) {
                paint.setColor(toBig.getColor());
            }
            drawModule(canvas, toBig, paint);
        }
    }

    /**
     * 监听点击事件
     *
     * @param event
     */
    public void clickZoomArea(MotionEvent event, float scale) {
        if (mOnZoomListener == null) {
            return;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Module toBig = zoomBox.getToBig();
            Module toSmall = zoomBox.getToSmall();
            float clickX = event.getX();
            float clickY = event.getY();
            if (RectUtils.isScope(clickX, clickY, toBig)) {
                float old = scale;
                float newZoom = old + zoomBox.getChangeSize();
                mOnZoomListener.onZoom(true, newZoom, old);
            } else if (RectUtils.isScope(clickX, clickY, toSmall)) {
                float old = scale;
                float newZoom = old - zoomBox.getChangeSize();
                mOnZoomListener.onZoom(true, newZoom, old);
            }

        }
    }

    /**
     * 绘制 模块
     *
     * @param canvas
     * @param module 需要绘制的模块
     */
    private void drawModule(Canvas canvas, Module module, Paint paint) {
        float x = module.getX();
        float endX = module.getEndX();
        float y = module.getY();
        float endY = module.getEndY();


        int divWidth = module.getDivWidth();
        if (divWidth > 0) {
            paint.setColor(module.getDivColor());
            canvas.drawRect(x - divWidth, y - divWidth, endX + divWidth, endY + divWidth, paint);
        }

        paint.setColor(module.getColor());
        if (paint.getStyle() != Paint.Style.FILL) {
            paint.setStyle(Paint.Style.FILL);
        }
        canvas.drawRect(x, y, endX, endY, paint);
    }


    private void initZoomButton() {
        Module bigModule = new Module();
        Module smallModule = new Module();

        zoomBox.setToBig(bigModule);
        zoomBox.setToSmall(smallModule);
    }

    private void resetLoaction() {
        Module toBig = zoomBox.getToBig();
        Module toSmall = zoomBox.getToSmall();

        float x = zoomBox.getX() + zoomBox.getPaddingLeft();
        float endX = zoomBox.getEndX() - zoomBox.getPaddingRight();

        toBig.setX(x);
        toBig.setEndX(endX);

        toSmall.setX(x);
        toSmall.setEndX(endX);

        float toBigY = zoomBox.getY() + zoomBox.getPaddingTop();
        float toBigEndY = toBigY + zoomBox.getBigButtonHeight();
        toBig.setY(toBigY);
        toBig.setEndX(toBigEndY);

        float toSmallY = toBigEndY + zoomBox.getDivWidth();
        float toSmallEndY = toSmallY + zoomBox.getSmallButtonHeight();

        toSmall.setY(toSmallY);
        toSmall.setEndY(toSmallEndY);
    }

    /**
     * 用于监听缩放事件
     */
    public interface OnZoomListener {
        /**
         * 监听放大缩小事件
         *
         * @param isToBig 当前是否是放大 true 是放大，false，是缩小
         * @param newZoom 变化后的值
         * @param oldZoom 变化前
         */
        void onZoom(boolean isToBig, float newZoom, float oldZoom);
    }
}
