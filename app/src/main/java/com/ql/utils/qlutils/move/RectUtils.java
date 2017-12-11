package com.ql.utils.qlutils.move;

/**
 * 创建时间:2017/12/11
 * 描述:
 *
 * @author ql
 */

public class RectUtils {
    /**
     * 用于判断 该点是否在  矩形区域内
     *
     * @param upX
     * @param upY
     * @param rect
     */
    public static  <T extends RectPoint> boolean isScope(float upX, float upY, T rect) {

        if ((upX > rect.getX()) && (upX < rect.getEndX()) && (upY > rect.getY()) && (upY < rect.getEndY())) {
            return true;
        }
        return false;
    }
}
