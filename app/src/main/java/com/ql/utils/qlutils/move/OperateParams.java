package com.ql.utils.qlutils.move;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2017/12/6
 * 描述:
 *
 * @author ql
 */

public class OperateParams {

    private List<Module> modules;
    private int moveStatus;

    private Module operateModule;//当前需要移动的 模块

    private boolean showBox; //内部放置需要移动module模块

    private boolean isOpenCoverage = true;

    public boolean isOpenCoverage() {
        return isOpenCoverage;
    }

    public void setOpenCoverage(boolean openCoverage) {
        isOpenCoverage = openCoverage;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public int getMoveStatus() {
        return moveStatus;
    }

    public void setMoveStatus(int moveStatus) {
        this.moveStatus = moveStatus;
    }

    public Module getOperateModule() {
        return operateModule;
    }

    public void setOperateModule(Module operateModule) {
        this.operateModule = operateModule;
    }

    public boolean isShowBox() {
        return showBox;
    }

    public void setShowBox(boolean showBox) {
        this.showBox = showBox;
    }

}
