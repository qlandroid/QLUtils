package com.ql.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by android on 2017/11/9.
 */

public abstract class RvCommonAdapter extends RecyclerView.Adapter {
    public static final int TYPE_NO_DATA = 21;//没有数据
    public static final int POSITION_NO_DATA = 1;

    public static final int TYPE_HEADER = 22;//头部信息
    public static final int POSITION_HEADER = 0;
    public static final int TYPE_FOOT = 23;//尾部
    public static int POSITION_FOOT;
    public static final int TYPE_ERROR = 23;//异常信息;
    public static final int POSITION_ERROR = 2;

    public static final int TYPE_FOOT_NO_DATA = 24;
    public static int POSITION_FOOT_NO_DATA;
    private View headerView;
    private View errorView;
    private View noDataView;
    private View footView;
    private View footNoDataView;

    private List data;

    public RvCommonAdapter() {
        changeFootPosition();
    }

    private void changeFootPosition() {
        POSITION_FOOT = getItemCount() - 1;
        POSITION_FOOT_NO_DATA = POSITION_FOOT - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (POSITION_HEADER == position) {
            return TYPE_HEADER;
        }
        if (POSITION_ERROR == position) {
            return TYPE_ERROR;
        }
        if (POSITION_NO_DATA == position) {
            return TYPE_NO_DATA;
        }
        if (POSITION_FOOT == position) {
            return TYPE_FOOT;
        }
        if (POSITION_FOOT_NO_DATA == position) {
            return TYPE_FOOT_NO_DATA;
        }

        return super.getItemViewType(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_HEADER == viewType) {
            return new HeaderViewHolder(headerView);
        }
        if (TYPE_NO_DATA == viewType) {
            return new HeaderViewHolder(footView);
        }
        if (TYPE_ERROR == viewType) {
            return new HeaderViewHolder(errorView);
        }
        if (TYPE_FOOT == viewType) {
            return new HeaderViewHolder(footView);
        }

        if (TYPE_FOOT_NO_DATA == viewType) {
            return new HeaderViewHolder(footNoDataView);
        }

        return createViewHolders(parent, viewType);
    }

    public abstract RecyclerView.ViewHolder createViewHolders(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (POSITION_HEADER == position) {
            return;
        }
        if (POSITION_NO_DATA == position) {
            return;
        }
        if (POSITION_ERROR == position) {
            return;
        }
        if (POSITION_FOOT == position) {
            return;
        }
        if (POSITION_FOOT_NO_DATA == position) {
            return;
        }
    }

    @Override
    public int getItemCount() {
        int size = data == null ? 0 : data.size();
        return size + getTypeCount();
    }

    /**
     * 添加到 尾部信息 的 角标位置
     *
     * @param position
     * @return
     */
    private boolean getFootPosition(int position) {
        return getItemCount() - 1 == position;
    }

    /**
     * 添加类型的数量
     *
     * @return
     */
    private int getTypeCount() {
        return 4;
    }

    public static class HeaderViewHolder extends BaseViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ErrorViewHolder extends BaseViewHolder {

        public ErrorViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class NoDataViewHolder extends BaseViewHolder {

        public NoDataViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FootViewHolder extends BaseViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FootNoDataViewHolder extends BaseViewHolder {

        public FootNoDataViewHolder(View itemView) {
            super(itemView);
        }
    }
}
