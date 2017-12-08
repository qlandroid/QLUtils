package com.ql.utils.qlutils.move;

/**
 * 创建时间:2017/12/8
 * 描述:
 *
 * @author ql
 */

public class RectPoint {
    float x;
    float y;
    float endX;
    float endY;

    float height;
    float width;

    public void resetSize(){
        width = endX - x;
        height = endY - y;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }
}
