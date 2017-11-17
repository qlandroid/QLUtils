package com.ql.utils.qlutils.function;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ql.utils.qlutils.R;
import com.ql.utils.qlutils.frag.NavFragment;
import com.ql.view.NavigationGroupView;
import com.ql.view.base.QLActivity;
import com.ql.view.base.QLFragment;
import com.ql.view.bind.BindView;

import java.util.HashMap;
import java.util.Map;

public class NavActivity extends QLActivity {

    private final int TAG_BTN_0 = 0;
    private final int TAG_BTN_1 = 1;
    private final int TAG_BTN_2 = 2;

    @BindView(R.id.nav_navigationGroupView)
    NavigationGroupView nGroupView;


    private Map<Integer,QLFragment> mFragmentMap;
    @Override
    protected void createView() {
        setContentView(R.layout.activity_nav);
    }

    @Override
    public void initData() {
        super.initData();
        mFragmentMap = new HashMap<>();
        mFragmentMap.put(TAG_BTN_0,NavFragment.newInstance(TAG_BTN_0));
        mFragmentMap.put(TAG_BTN_2,NavFragment.newInstance(TAG_BTN_2));
        mFragmentMap.put(TAG_BTN_1,NavFragment.newInstance(TAG_BTN_1));


    }

    @Override
    public void initView() {
        super.initView();
        nGroupView.setTextColor(Color.GRAY,Color.RED);
        addBottomNavigationButton();

        nGroupView.setOnClickButtonListener(new NavigationGroupView.OnClickButtonListener() {
            @Override
            public void onClickSelectButton(int tag) {
                changeFragment(R.id.nav_fl_content,mFragmentMap.get(tag));
            }
        });

        nGroupView.setSelectButtonTag(TAG_BTN_0);
    }

    private void addBottomNavigationButton() {
        nGroupView.addButton(TAG_BTN_0,"首页",R.mipmap.home_btn_normal,R.mipmap.home_btn_select);
        nGroupView.addButton(TAG_BTN_1,"主页",R.mipmap.home_btn_normal,R.mipmap.home_btn_select);
        nGroupView.addButton(TAG_BTN_2,"个人",R.mipmap.home_btn_normal,R.mipmap.home_btn_select);
    }
}
