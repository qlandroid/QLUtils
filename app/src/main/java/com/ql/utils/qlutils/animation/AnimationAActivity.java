package com.ql.utils.qlutils.animation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;

import com.ql.utils.qlutils.R;
import com.ql.view.Layout;
import com.ql.view.base.QLBindLayoutActivity;
import com.ql.view.bind.BindView;

@Layout(R.layout.activity_animation_a)
public class AnimationAActivity extends QLBindLayoutActivity {

    @BindView(R.id.activity_animation_a_v)
    View v;

    @Override
    protected void createView() {
        super.createView();
    }

    public void clickNext(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            b();
        } else {
            Intent intent = new Intent(this, AnimationBActivity.class);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void b() {
        Intent intent = new Intent(this, AnimationBActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void clickNextShare(View view) {
        Intent intent = new Intent(this, AnimationBActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, v, "animation_v").toBundle());
        /**
         * ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
         Pair.create(view1, "agreedName1"),
         Pair.create(view2, "agreedName2"));
         创建多个View 的动画
         */
    }
}
