package com.ql.view.base;

import com.ql.view.LayoutUtils;

/**
 * @author ql
 *         创建时间:2017/11/28
 *         描述:
 */

public class QLBindLayoutActivity extends QLActivity {
    @Override
    protected void createView() {
        LayoutUtils.bind(this);
    }
}
