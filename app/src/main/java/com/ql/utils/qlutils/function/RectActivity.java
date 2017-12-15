package com.ql.utils.qlutils.function;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.ql.utils.qlutils.R;
import com.ql.view.Layout;
import com.ql.view.base.QLBindLayoutActivity;
import com.ql.view.bind.BindView;

@Layout(R.layout.activity_rect)
public class RectActivity extends QLBindLayoutActivity {
    Rect srcRect = new Rect();
    Rect finalRect = new Rect();
    Point point = new Point();
    float widthScale, heightScale;
    @BindView(R.id.activity_rect_img)
    ImageView iv;
    @BindView(R.id.activity_rect_img_0)
    ImageView iv0;
    @BindView(R.id.activity_rect_img_1)
    ImageView iv1;
    @BindView(R.id.activity_rect)
    ViewGroup vg;
    @BindView(R.id.activity_rect_group)
    ViewGroup viewGroup;

    @Override
    public void initView() {
        super.initView();
        iv0.setOnClickListener(this);
        iv1.setOnClickListener(this);

        iv.setOnClickListener(this);
    }

    @Override
    public void clickWidget(View v) {
        super.clickWidget(v);
        switch (v.getId()) {
            case R.id.activity_rect_img:
                hideAnimation(v);
                break;
            case R.id.activity_rect_img_0:
                toAnimation(v, R.drawable.test_icon);
                break;
            case R.id.activity_rect_img_1:
                toAnimation(v, R.mipmap.ic_launcher);
                break;
            default:
        }
    }

    private void hideAnimation(View targetView) {
        iv.setClickable(false);
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(targetView, View.X, finalRect.left, srcRect.left))
                .with(ObjectAnimator.ofFloat(targetView, View.Y, finalRect.top, finalRect.top))
                .with(ObjectAnimator.ofFloat(targetView, View.SCALE_X, 1f, widthScale))
                .with(ObjectAnimator.ofFloat(targetView, View.SCALE_Y, 1f, heightScale))
                .with(ObjectAnimator.ofFloat(viewGroup, View.ALPHA, 1.0f, 0f))
        ;
        set.setDuration(400);
        set.setInterpolator(new DecelerateInterpolator());
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                iv.setClickable(true);
                iv.setVisibility(View.GONE);
                viewGroup.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });

    }

    private void toAnimation(View img, int resId) {
        img.getGlobalVisibleRect(srcRect);//获得当前控件的绘制区域，坐标点，保存到srcRect 矩形区域中
        vg.getGlobalVisibleRect(finalRect, point);//因为是放大到全屏区域，所以将总布局的大小
        viewGroup.setVisibility(View.VISIBLE);// 显示背景

        srcRect.offset(-point.x, -point.y);//设置左上角的偏移量
        finalRect.offset(-point.x, -point.y);//设置左上角的偏移量
        //设置缩放时 按照哪个点进行缩放
        iv.setPivotX(0f);
        iv.setPivotY(0f);
        iv.setVisibility(View.VISIBLE);
        iv.setImageResource(resId);
        //获得宽高的比例
        widthScale = srcRect.width() * 1.0f / finalRect.width();
        heightScale = srcRect.height() * 1.0f / finalRect.height();

        //设置动画
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(iv, View.X, srcRect.left, finalRect.left))//横向移动
                .with(ObjectAnimator.ofFloat(iv, View.Y, srcRect.top, finalRect.top))//纵向移动
                .with(ObjectAnimator.ofFloat(iv, View.SCALE_X, widthScale, 1.0f))//横向的放大比例
                .with(ObjectAnimator.ofFloat(iv, View.SCALE_Y, heightScale, 1.0f))//纵向的放大比例
                .with(ObjectAnimator.ofFloat(viewGroup, View.ALPHA, 0, 1.0f))//背景颜色 进行渐变
        ;
        set.setDuration(400);//设置动画时间
        set.setInterpolator(new DecelerateInterpolator());
        set.start();

    }
}
