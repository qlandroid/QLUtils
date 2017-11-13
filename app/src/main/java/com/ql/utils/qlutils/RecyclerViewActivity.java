package com.ql.utils.qlutils;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ql.view.base.QLActivity;
import com.ql.view.bind.BindView;

public class RecyclerViewActivity extends QLActivity {

    @BindView(R.id.rv_list)
    RecyclerView rv;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void createView() {
        setContentView(R.layout.activity_recycler_view);
    }

    @Override
    public void initView() {
        super.initView();
        rv.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

    }

}
