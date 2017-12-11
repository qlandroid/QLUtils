package com.ql.utils.qlutils.move;

/**
 * 创建时间:2017/12/8
 * 描述:
 *
 * @author ql
 */

public interface IOperate {

    /**
     * 用于设置当前 操作
     *
     * @param status 当前操作状态
     * @see MoveStatus 状态值
     */
    void setStatus(int status);

    /**
     * 回到原点
     */
    void toZero();

    /**
     * 设置当前显示的比例值
     *
     * @param zoom
     */
    void setZoom(float zoom);
}
