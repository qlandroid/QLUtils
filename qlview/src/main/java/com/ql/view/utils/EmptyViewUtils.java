package com.ql.view.utils;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.IntegerRes;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ql.view.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by android on 2017/11/14.
 */

public class EmptyViewUtils {
    @IntDef({TYPE_LOADING, TYPE_ERROR, TYPE_NO_DATA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static final int TYPE_LOADING = 0X0000222;
    public static final int TYPE_ERROR = 0X0000223;
    public static final int TYPE_NO_DATA = 0X0000224;

    private int type;
    private View loadingView;
    private View errorView;
    private View noDataView;


    public void createDef(LayoutInflater li, RecyclerView rv) {
        loadingView = li.inflate(R.layout.loading, (ViewGroup) rv, false);
        errorView = li.inflate(R.layout.error, (ViewGroup) rv, false);
        noDataView = li.inflate(R.layout.no_data, (ViewGroup) rv, false);
    }


    public void setType(@Type int type) {
        this.type = type;
    }

    public View getLoadingView() {
        return loadingView;
    }

    public View getErrorView() {
        return errorView;
    }

    public View getNoDataView() {
        return noDataView;
    }
}
