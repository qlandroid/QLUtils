package com.ql.utils.qlutils.move;

/**
 * 创建时间:2017/12/6
 * 描述:移动模块的信息
 *
 * @author ql
 */

public class Module {
    private int x;
    private int endX;
    private int y;
    private int endY;

    private int color;

    private String data;//描述信息

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getEndX() {
        return endX;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }
}
