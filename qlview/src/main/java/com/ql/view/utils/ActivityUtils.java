package com.ql.view.utils;

import com.ql.view.base.QLActivity;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017-4-28.
 */
public class ActivityUtils {
    private static ActivityUtils mUtils = null;
    public ArrayList<QLActivity> atyList;

    public static ActivityUtils getInstance() {
        if (mUtils == null) {
            synchronized (ActivityUtils.class) {
                if (mUtils == null) {
                    mUtils = new ActivityUtils();
                }
            }
        }
        return mUtils;
    }

    public void exit() {
        if (atyList != null)
            for (int i = 0; i < atyList.size(); i++) {
                atyList.get(i).finish();
            }
        System.exit(0);
    }

    private ActivityUtils() {
        atyList = new ArrayList<>();
    }

    public void addAty(QLActivity aty) {
        atyList.add(aty);
    }

    public void removeAty(QLActivity aty) {
        atyList.remove(aty);
    }

    public void removeAllAty() {
        atyList.removeAll(null);
    }

    public QLActivity getTopAty() {
        return atyList.get(atyList.size() - 1);
    }
}
