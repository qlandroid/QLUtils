package com.ql.utils.qlutils;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ql.view.base.QLActivity;

public class SystemBarActivity extends QLActivity {


    @Override
    public int getStatusBarTintColor() {
        //设置system的颜色
        return Color.RED;
    }

    @Override
    protected void createView() {
        setContentView(R.layout.activity_system_bar);
    }
}
