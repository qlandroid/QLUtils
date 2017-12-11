package com.ql.utils.qlutils.move;

import java.util.List;

/**
 * 创建时间:2017/12/8
 * 描述:
 *
 * @author ql
 */

public class BoxOperate {


    /**
     * 将模块添加到当前 盒子中
     *
     * @param upX           松手后的位置 x
     * @param upY           松手后的位置 y
     * @param operateModule 原始的模块
     * @param box
     */
    public void addModule(float upX, float upY, Module operateModule, Box box) {
        List<Module> boxSaveModule = box.getBoxSaveModule();
        float inX = box.getPaddingLeft() + box.getX() + box.getDivWidth();
        float lastInX = 0;
        boolean isAddModule = false;
        for (int i = 0; i < boxSaveModule.size(); i++) {
            Module boxInModule = boxSaveModule.get(i);
            lastInX = inX;
            inX += boxInModule.getWidth() + box.getItemWidth();
            if (isScopeIn(upX, upY, boxInModule, box)) {
                //在当前矩形区域内
                //判断是该矩形是添加在 该某块的左边还是右边，
                float x = boxInModule.getX();
                float endX = boxInModule.getEndX();
                float moduleCenterX = (x + endX) / 2;
                Module addModuleFromBox = new Module(operateModule);
                if (upX < moduleCenterX) {
                    resetAddModuleFromBox(addModuleFromBox, box, lastInX);
                    box.getBoxSaveModule().add(i, addModuleFromBox);
                    box.getSrcModule().add(i, operateModule);
                    toResetModuleOfIndexAfter(i + 1, box, lastInX + addModuleFromBox.getWidth() + box.getItemWidth());
                } else {
                    //添加到模块的右边
                    resetAddModuleFromBox(addModuleFromBox, box, inX);
                    box.getBoxSaveModule().add(i + 1, addModuleFromBox);
                    box.getSrcModule().add(i + 1, operateModule);
                    toResetModuleOfIndexAfter(i + 2, box, inX + addModuleFromBox.getWidth() + box.getItemWidth());
                }
                isAddModule = true;
                break;
            }
        }
        if (!isAddModule) {
            Module addModuleFromBox = new Module(operateModule);
            resetAddModuleFromBox(addModuleFromBox, box, inX);
            box.getBoxSaveModule().add(addModuleFromBox);
            box.getSrcModule().add(operateModule);
        }

    }

    /**
     * 是否在区域内，包含 左右 内边距
     *
     * @param upX
     * @param upY
     * @param rect
     * @param box
     * @return
     */
    public <T extends RectPoint> boolean isScopeIn(float upX, float upY, T rect, Box box) {
        if ((upX > rect.getX() - box.getItemWidth()) && (upX < rect.getEndX() + box.getItemWidth()) && (upY > rect.getY() - box.getPaddingTop()) && (upY < rect.getEndY() + box.getPaddingBottom())) {
            return true;
        }
        return false;
    }

    private void toResetModuleOfIndexAfter(int index, Box box, float left) {
        List<Module> boxSaveModule = box.getBoxSaveModule();
        if (boxSaveModule.size() >= index) {
            return;
        }
        for (int i = index; i < boxSaveModule.size(); i++) {
            Module module = boxSaveModule.get(i);
            resetAddModuleFromBox(module, box, left);
        }
    }

    private void resetAddModuleFromBox(Module addBoxInModule, Box box, float lastInX) {
        //应该添加到该模块的左边
        //计算当前模块 的宽高；如果 高度大于当前盒子模型的高度，那么就需要将模块的大小按比例缩小
        float boxContentHeight = box.getEndY() - box.getY() - box.getPaddingTop() - box.getPaddingBottom();

        if (addBoxInModule.getHeight() > boxContentHeight) {
            float boxInWidth = boxContentHeight / addBoxInModule.getHeight() * addBoxInModule.getWidth();
            addBoxInModule.setWidth(boxInWidth);
            addBoxInModule.setHeight(boxContentHeight);
        }
        if (addBoxInModule.getWidth() > box.getMaxItemWidth()) {
            float scaleHeight = box.getMaxItemWidth() / addBoxInModule.getWidth() * addBoxInModule.getHeight();
            addBoxInModule.setHeight(scaleHeight);
        }
        float y = 0, endY = 0;
        switch (box.getAddItemMode()) {
            case Box.ADD_ITEM_MODE_CENTER:
                float boxContentCenterY = (box.getPaddingTop() - box.getPaddingBottom() + box.y + box.getEndY()) / 2;
                y = boxContentCenterY - addBoxInModule.getHeight() / 2;
                endY = y + addBoxInModule.getHeight();
                break;
            case Box.ADD_ITEM_MODE_UP:
                y = box.getPaddingTop() + box.getY();
                endY = y + addBoxInModule.getHeight();
                break;
            case Box.ADD_ITEM_MODE_DOWN:
                endY = box.getEndY() - box.getPaddingBottom();
                y = endY - addBoxInModule.getHeight();
                break;
            default:
        }

        addBoxInModule.setX(lastInX);
        addBoxInModule.setEndX(lastInX + addBoxInModule.getWidth());
        addBoxInModule.setY(y);
        addBoxInModule.setEndY(endY);
    }


    /**
     * 将模块从盒子中移除
     *
     * @param module
     * @param box
     */
    public void removeModuleFromBox(Module module, Box box) {
        box.getSrcModule().remove(module);
        box.getBoxSaveModule().remove(module);
    }

    /**
     * 将模块从盒子中移除
     *
     * @param box
     */
    public void removeModuleFromBox(int moduleIndex, Box box) {
        box.getSrcModule().remove(moduleIndex);
        box.getBoxSaveModule().remove(moduleIndex);
        float left = box.getPaddingLeft() + box.getX();
        for (Module module : box.getBoxSaveModule()) {
            resetAddModuleFromBox(module, box, left);
            module.resetSize();
            left = +module.getWidth() + box.getItemWidth();
        }
    }

}
