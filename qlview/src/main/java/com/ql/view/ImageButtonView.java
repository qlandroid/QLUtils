package com.ql.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * Created by Administrator on 2016/11/26.
 */
public class ImageButtonView extends LinearLayout {
    public ImageButtonView(Context context) {
        this(context, null, 0);
    }

    public ImageButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private TextView mTvName;
    private ImageView mImageView;
    private Context mContext;
    private int selectColor;
    private int normalColor;
    private int selectIcon;
    private int normalIcon;

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        mTvName = new TextView(context);
        mImageView = new ImageView(context);
        this.setOrientation(LinearLayout.VERTICAL);
        this.addView(mImageView);
        this.addView(mTvName);
        this.setClickable(true);//设置可点击
        LayoutParams llLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.setLayoutParams(llLp);

        mTvName.setGravity(Gravity.CENTER);
        setNameSize(14);
        setName("name");

        LayoutParams imageLp = new LayoutParams(LayoutParams.MATCH_PARENT
                , 0, 1);
        imageLp.gravity = Gravity.CENTER;
        mImageView.setLayoutParams(imageLp);
        LayoutParams tvNameLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mTvName.setMaxLines(1);
        tvNameLp.setMargins(0,0,0,6);
        mTvName.setLayoutParams(tvNameLp);
    }

    public void setName(String name) {
        mTvName.setText(name);
    }

    public void setNameSize(int size) {
        mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setNameColor(int selectColor,int normalColor) {
        this.selectColor = selectColor;
        this.normalColor = normalColor;
    }

    public void setName(int id) {
        String name = mContext.getResources().getString(id);
        setName(name);
    }

    public void setStatus(boolean status){
        if (status){
            mTvName.setTextColor(selectColor);
            mImageView.setImageResource(selectIcon);
            return;
        }
        mTvName.setTextColor(normalColor);
        mImageView.setImageResource(normalIcon);

    }

    public TextView getTextView() {
        return mTvName;
    }

    public void setmTvName(TextView mTvName) {
        this.mTvName = mTvName;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setmImageView(ImageView mImageView) {
        this.mImageView = mImageView;
    }

    /**
     * 设置图片
     */
    public void setSelectImageView(int selectIcon,int normalIcon) {
        this.selectIcon = selectIcon;
        this.normalIcon =normalIcon;
    }

    public void setImageViewIcon(int id) {
        mImageView.setImageResource(id);
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mTvName.setEnabled(enabled);
        mImageView.setEnabled(enabled);
    }
}
