package com.ql.utils.qlutils.function;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ql.utils.qlutils.R;
import com.ql.utils.qlutils.widget.ExpandableTextView;
import com.ql.view.Layout;
import com.ql.view.base.QLActivity;
import com.ql.view.base.QLBindLayoutActivity;
import com.ql.view.bind.BindView;

@Layout(value = R.layout.activity_expandable_text_view,title = "Expandable")
public class ExpandableTextViewActivity extends QLBindLayoutActivity {

    @BindView(R.id.expandable_group)
    ExpandableTextView tvGroup;

    @Override
    public void initView() {
        super.initView();

        tvGroup.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {

            }
        });
        tvGroup.setText(getString(R.string.dummy_text1));
    }
}
