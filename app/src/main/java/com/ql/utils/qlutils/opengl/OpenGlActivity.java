package com.ql.utils.qlutils.opengl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ql.utils.qlutils.R;
import com.ql.view.Layout;
import com.ql.view.base.QLActivity;
import com.ql.view.base.QLBindLayoutActivity;

public class OpenGlActivity extends QLActivity {

    @Override
    protected void createView() {
        OpenGL gl = new OpenGL(this);
        setContentView(gl);
    }
}
