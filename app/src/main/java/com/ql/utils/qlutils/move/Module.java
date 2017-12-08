package com.ql.utils.qlutils.move;

import android.graphics.Color;

/**
 * 创建时间:2017/12/6
 * 描述:移动模块的信息
 *
 * @author ql
 */

public class Module extends RectPoint {

    private int color;

    private int divColor;
    private int divWidth;
    private String data;//描述信息


    public Module() {
    }

    public Module(Module module) {
        x = module.x;
        endX = module.endX;
        y = module.y;
        endY = module.endY;
        color = module.color;
        divColor = Color.RED;
        divWidth = 5;
        data = module.data;
        width = endX - x;
        height = endY - y;
    }

    public int getDivWidth() {
        return divWidth;
    }

    public void setDivWidth(int divWidth) {
        this.divWidth = divWidth;
    }

    public int getDivColor() {
        return divColor;
    }

    public void setDivColor(int divColor) {
        this.divColor = divColor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


}
