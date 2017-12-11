package com.ql.utils.qlutils.move;

/**
 * 创建时间:2017/12/11
 * 描述:
 *
 * @author ql
 */

public class ZoomBox extends RectPoint {
    private float bigButtonHeight;
    private float smallButtonHeight;

    /**
     * 放大区域
     */
    private Module toBig;
    /**
     * 缩小区域
     */
    private Module toSmall;

    /**
     * 每次改变得数量
     */
    private int changeSize = 1;
    /**
     * 按钮质检的间距
     */
    private float divWidth;

    public ZoomBox() {

        bigButtonHeight = smallButtonHeight = 30;

        divWidth = 10;

    }

    public Module getToBig() {
        return toBig;
    }

    public void setToBig(Module toBig) {
        this.toBig = toBig;
    }

    public Module getToSmall() {
        return toSmall;
    }

    public void setToSmall(Module toSmall) {
        this.toSmall = toSmall;
    }

    public float getDivWidth() {
        return divWidth;
    }

    public void setDivWidth(float divWidth) {
        this.divWidth = divWidth;
    }

    public float getBigButtonHeight() {
        return bigButtonHeight;
    }

    public void setBigButtonHeight(float bigButtonHeight) {
        this.bigButtonHeight = bigButtonHeight;
    }

    public float getSmallButtonHeight() {
        return smallButtonHeight;
    }

    public void setSmallButtonHeight(float smallButtonHeight) {
        this.smallButtonHeight = smallButtonHeight;
    }

    public int getChangeSize() {
        return changeSize;
    }

    public void setChangeSize(int changeSize) {
        this.changeSize = changeSize;
    }
}
