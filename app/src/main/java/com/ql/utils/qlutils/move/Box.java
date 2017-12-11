package com.ql.utils.qlutils.move;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2017/12/6
 * 描述: 一个收纳盒，用于保存需要移动的模块
 *
 * @author ql
 */

public class Box extends RectPoint {
    public static final int ADD_ITEM_MODE_UP = 2;
    public static final int ADD_ITEM_MODE_CENTER = 3;
    public static final int ADD_ITEM_MODE_DOWN = 4;
    public static final int HIDEL_MODULE_CLEAR = -1;

    private int bgColor;//当前盒子的背景颜色
    private int divColor;//边框颜色
    private int divWidth;//边框宽度

    private int addItemMode = ADD_ITEM_MODE_CENTER;//添加模块显示的方式 居上，居中，居下

    private float maxItemWidth;

    private float moveToBoxX, moveToBoxY;

    private int hideModuleIndex = -1;//用于隐藏某个模块， 如移动时， 将该模块隐藏， 当拖拽失败是，显示


    private float itemWidth;//每个模块的间距



    private Module moveModule;//移动 的模块，需要放在盒子内

    private List<Module> srcModule = new ArrayList<>();//原始的数据
    private List<Module> boxSaveModule = new ArrayList<>();//在盒子中存储的数据

    public Box() {
        bgColor = Color.CYAN;
        divColor = Color.YELLOW;
        divWidth = 10;
        x = 40;
        y = 40;
        endX = 300;
        endY = 200;
        itemWidth = 20;
        maxItemWidth = 150;
        setPadding(20);
    }

    /**
     * 清空隐藏效果
     */
    public void clearHideModuleIndex() {
        hideModuleIndex = HIDEL_MODULE_CLEAR;
    }

    public float getMaxItemWidth() {
        return maxItemWidth;
    }

    public void setMaxItemWidth(float maxItemWidth) {
        this.maxItemWidth = maxItemWidth;
    }

    public List<Module> getSrcModule() {
        return srcModule;
    }

    public void setSrcModule(List<Module> srcModule) {
        this.srcModule = srcModule;
    }

    public List<Module> getBoxSaveModule() {
        return boxSaveModule;
    }

    public void setBoxSaveModule(List<Module> boxSaveModule) {
        this.boxSaveModule = boxSaveModule;
    }

    public float getMoveToBoxX() {
        return moveToBoxX;
    }

    public void setMoveToBoxX(float moveToBoxX) {
        this.moveToBoxX = moveToBoxX;
    }

    public float getMoveToBoxY() {
        return moveToBoxY;
    }

    public void setMoveToBoxY(float moveToBoxY) {
        this.moveToBoxY = moveToBoxY;
    }

    public int getDivColor() {
        return divColor;
    }

    public void setDivColor(int divColor) {
        this.divColor = divColor;
    }

    public int getDivWidth() {
        return divWidth;
    }

    public void setDivWidth(int divWidth) {
        this.divWidth = divWidth;
    }


    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public Module getMoveModule() {
        return moveModule;
    }

    public void setMoveModule(Module moveModule) {
        this.moveModule = moveModule;
    }

    public int getAddItemMode() {
        return addItemMode;
    }

    public void setAddItemMode(int addItemMode) {
        this.addItemMode = addItemMode;
    }

    public int getHideModuleIndex() {
        return hideModuleIndex;
    }

    public void setHideModuleIndex(int hideModuleIndex) {
        this.hideModuleIndex = hideModuleIndex;
    }

    public void setPadding(float padding) {
        paddingLeft = padding;
        paddingTop = padding;
        paddingRight = padding;
        paddingBottom = padding;
    }

    public float getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(float itemWidth) {
        this.itemWidth = itemWidth;
    }


}
