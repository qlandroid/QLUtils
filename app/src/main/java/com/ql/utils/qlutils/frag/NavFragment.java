package com.ql.utils.qlutils.frag;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ql.utils.qlutils.R;
import com.ql.view.base.QLFragment;
import com.ql.view.bind.BindView;

/**
 * Created by mrqiu on 2017/7/16.
 */

public class NavFragment extends QLFragment {

    private static final String TAG = "tag";


    public static QLFragment newInstance(int tag) {

        Bundle args = new Bundle();

        QLFragment fragment = new NavFragment();

        args.putInt(TAG,tag);
        fragment.setArguments(args);
        return fragment;
    }


    @BindView(R.id.frag_nav_tag)
    TextView tvTag;

    private int tag;

    @Override
    public int createView() {
        return R.layout.frag_nav;
    }

    @Override
    public void initData() {
        super.initData();
        tag = getArguments().getInt(TAG);
    }

    @Override
    public void initWidget(View view) {
        super.initWidget(view);
        tvTag.setText("选中的是 ---->>" + tag);
    }
}
