package com.ql.utils.qlutils.function;

import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

import com.ql.utils.qlutils.R;
import com.ql.view.Layout;
import com.ql.view.base.QLActivity;
import com.ql.view.base.QLBindLayoutActivity;
import com.ql.view.bind.BindView;

@Layout(R.layout.activity_reveal_effect)
public class RevealEffectActivity extends QLBindLayoutActivity {
    @BindView(R.id.activity_reveal_effect)
    View tv;
    private Animator anim;

    @Override
    public void initView() {
        super.initView();
        // previously invisible view
        tv.postDelayed(new Runnable() {
            @Override
            public void run() {
                // get the center for the clipping circle
                int cx = (tv.getLeft() + tv.getRight()) / 2;
                int cy = (tv.getTop() + tv.getBottom()) / 2;

// get the final radius for the clipping circle
                int finalRadius = tv.getWidth();

// create and start the animator for this view
// (the start radius is zero)
                anim =
                        ViewAnimationUtils.createCircularReveal(tv, cx, cy, 0, finalRadius);
                anim.start();
            }
        },1000);

    }
}
