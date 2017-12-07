package com.ql.utils.qlutils.move;

/**
 * 创建时间:2017/12/7
 * 描述:
 *
 * @author ql
 */

public class MoveParams {
    private float moveX;//手指进行移动 X横向距离
    private float moveY;//手指进行移动的 Y纵向距离

    public float getMoveX() {
        return moveX;
    }

    public void setMoveX(float moveX) {
        this.moveX = moveX;
    }

    public float getMoveY() {
        return moveY;
    }

    public void setMoveY(float moveY) {
        this.moveY = moveY;
    }


    /**
     * 添加移动后的距离
     *
     * @param moveX
     * @param moveY
     */
    public void addMove(float moveX, float moveY) {
        this.moveX = this.moveX + moveX;
        this.moveY = this.moveY + moveY;
    }
}
