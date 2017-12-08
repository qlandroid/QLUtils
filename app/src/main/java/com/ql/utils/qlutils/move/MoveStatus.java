package com.ql.utils.qlutils.move;

/**
 * 创建时间:2017/12/6
 * 描述:当前进行的操作
 * 缩放动作：1.放大
 * 2.缩小
 * 移动页面动作
 * 移动模块动作
 *
 * @author ql
 */

public class MoveStatus {
    /**
     * 当前需要做缩放动作
     */
    public static final int SCALE = 0X2222;
    /**
     * 当前需要做移动动作
     */
    public static final int MOVE = 0X2222;
    /**
     * 移动单个模块位置
     */
    public static final int MOVE_MODULE = 0X444;

}
