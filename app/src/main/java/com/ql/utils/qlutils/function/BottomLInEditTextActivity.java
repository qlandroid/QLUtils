package com.ql.utils.qlutils.function;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;

import com.ql.utils.qlutils.R;
import com.ql.view.Layout;
import com.ql.view.base.QLBindLayoutActivity;
import com.ql.view.bind.BindView;
import com.ql.view.utils.ToastUtils;

@Layout(R.layout.activity_bottom_lin_edit_text)
public class BottomLInEditTextActivity extends QLBindLayoutActivity {
    private static final String TAG = "BottomLInEditTextActivi";
    @BindView(R.id.activity_bottom_lin_edit_text_et_0)
    EditText et0;
    @BindView(R.id.activity_bottom_lin_edit_text_div_0)
    View v0;
    @BindView(R.id.activity_bottom_lin_edit_text_et_1)
    EditText et1;
    @BindView(R.id.activity_bottom_lin_edit_text_div_1)
    View v1;

    private ObjectAnimator showAnimator;
    private ObjectAnimator hideAnimator;

    boolean isFirst = true;

    @Override
    public void initView() {
        super.initView();

        showAnimator = ObjectAnimator.ofFloat(v0, "scaleX", 0f, 1f);
        showAnimator.setDuration(1_000);
        hideAnimator = ObjectAnimator.ofFloat(v0, "scaleX", 1f, 0f);
        hideAnimator.setDuration(1_000);

        showAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                View view = (View) showAnimator.getTarget();
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        hideAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd: ");
                showAnimator.start();
                View view = (View) hideAnimator.getTarget();
                view.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i(TAG, "onAnimationCancel: ");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i(TAG, "onAnimationRepeat: ");
            }
        });

        et0.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (isFirst) {
                    isFirst = false;
                    showAnimator.setTarget(v0);
                    showAnimator.start();
                } else if (!hasFocus) {
                    hideAnimator.cancel();
                    showAnimator.cancel();
                    hideAnimator.setTarget(v0);
                    showAnimator.setTarget(v1);
                    hideAnimator.start();
                }
            }
        });
        et1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (isFirst) {
                    isFirst = false;
                    showAnimator.setTarget(v1);
                    showAnimator.start();
                } else if (!hasFocus) {
                    hideAnimator.cancel();
                    showAnimator.cancel();
                    hideAnimator.setTarget(v1);
                    showAnimator.setTarget(v0);
                    hideAnimator.start();
                }
            }
        });

    }

    public void startAnimation(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ToastUtils.show(BottomLInEditTextActivity.this, "开始了");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ToastUtils.show(BottomLInEditTextActivity.this, "结束了");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                ToastUtils.show(BottomLInEditTextActivity.this, "重新开始了");

            }
        });
    }

    public void startValueAnimation(View view) {
    }
}
