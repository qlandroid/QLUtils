package com.ql.utils.qlutils.move;

import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * 创建时间:2017/12/7
 * 描述:
 *
 * @author ql
 */

public class ScaleParams {
    public static final float SCALE_MIN = 1f;
    public static final float SCALE_MAX = 14f;

    public static final float SCALE_NORMAL = 1f;

    private float scale = SCALE_NORMAL;//当前的放大倍数；
    private boolean isOperateScale;//当前是否是缩放状态

    private PointF middle;//两指中点坐标
    private float lastScale;


    /**
     * 是否改变放大倍数
     *
     * @return
     */
    public boolean isScaleChange() {
        return Math.abs(lastScale - scale) > 0.000004;
    }

    public float getLastScale() {
        return lastScale;
    }

    public void setLastScale(float lastScale) {
        this.lastScale = lastScale;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    private float scaleFactor;


    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.lastScale = this.scale;
        this.scale = scale;
    }

    public boolean isOperateScale() {
        return isOperateScale;
    }

    public void setOperateScale(boolean operateScale) {

        isOperateScale = operateScale;
    }


    // 计算两个触摸点的中点
    public PointF middle(MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        return new PointF(x / 2, y / 2);
    }

    // 计算两个触摸点之间的距离
    public double distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return Math.sqrt(x * x + y * y);
    }

    public PointF getMiddle() {
        return middle;
    }

    public void setMiddle(PointF middle) {
        this.middle = middle;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
}
