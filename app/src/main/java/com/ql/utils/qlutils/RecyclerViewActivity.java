package com.ql.utils.qlutils;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.ql.view.base.QLActivity;
import com.ql.view.bind.BindView;
import com.ql.view.rv.BaseQuickAdapter;
import com.ql.view.rv.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

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
        final RvAdapter rvAdapter = new RvAdapter();
        rv.setAdapter(rvAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rvAdapter.setEnableLoadMore(false);
                rv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addData(rvAdapter);
                        rvAdapter.setEnableLoadMore(true);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2_000);

            }
        });

        rvAdapter.notifyDataSetChanged();

        rvAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> data = rvAdapter.getData();
                        for (int i = 0; i < 10; i++) {
                            data.add("加载时间=" + System.currentTimeMillis());
                        }
                        rvAdapter.setNewData(data);
                        rvAdapter.notifyDataSetChanged();
                        rvAdapter.loadMoreEnd();
                    }
                }, 2_000);

            }
        }, rv);
        View emptyView = getLayoutInflater().inflate(R.layout.item_empty_rv, (ViewGroup) rv.getParent(), false);
        View viewById = emptyView.findViewById(R.id.item_empty_rv_iv);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addData(rvAdapter);
                    }
                }, 2_000);
            }
        });
        rvAdapter.setEmptyView(emptyView);

    }

    private void addData(RvAdapter rvAdapter) {
        List<String> l = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            l.add("item--->" + i);
        }
        rvAdapter.setNewData(l);
        rvAdapter.notifyDataSetChanged();
    }


    public static class RvAdapter extends BaseQuickAdapter<String, RvViewHolder> {

        public RvAdapter() {
            super(R.layout.item_test_rv, null);
        }

        @Override
        protected void convert(RvViewHolder helper, String item) {
            helper.setText(R.id.item_test_rv_tv, item);
        }
    }

    public static class RvViewHolder extends BaseViewHolder {

        public RvViewHolder(View view) {
            super(view);
        }
    }
}
