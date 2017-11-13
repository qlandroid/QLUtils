package com.ql.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by android on 2017/11/9.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T findView(int id) {
        return itemView.findViewById(id);
    }

    public BaseViewHolder setTextView(int id, CharSequence text) {
        TextView tv = itemView.findViewById(id);
        tv.setText(text);
        return this;
    }

    public BaseViewHolder setImageView(int id, int icon) {
        ImageView iv = itemView.findViewById(id);

        iv.setImageResource(icon);
        return this;
    }

}
