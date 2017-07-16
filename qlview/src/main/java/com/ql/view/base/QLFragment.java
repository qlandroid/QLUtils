package com.ql.view.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ql.view.bind.BindViewUtils;

/**
 * Created by mrqiu on 2017/7/15.
 */
public abstract class QLFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    private long click_last_time ;
    private static final long MIN_CLICK_DOUBLE = 300; //过滤 连续点击

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int viewID = createView();
        View view = inflater.inflate(viewID,container,false);
        return view;
    }
    public abstract int createView();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initData();
        BindViewUtils.find(this,view);
        initWidget(view);
    }

    @Override
    public void onClick(View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - click_last_time > MIN_CLICK_DOUBLE){
            clickWidget(v);
            click_last_time = clickTime;
        }
    }

    public void clickWidget(View v) {

    }

    public void initWidget(View view) {

    }

    public void initData() {

    }
}
