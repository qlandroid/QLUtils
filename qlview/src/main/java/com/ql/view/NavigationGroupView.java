package com.ql.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;


/**
 * Created by Administrator on 2016/11/26.
 */
public class NavigationGroupView extends LinearLayout implements View.OnClickListener {


    public NavigationGroupView(Context context) {
        this(context, null, 0);
    }

    public NavigationGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private int textDefColor = Color.GRAY, textSelectColor = Color.BLACK;

    private Context mContext;
    private OnClickButtonListener mOnClickButtonListener;
    private int lastTag = -1;

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        this.setOrientation(LinearLayout.HORIZONTAL);
    }

    public void setOnClickButtonListener(OnClickButtonListener l) {
        this.mOnClickButtonListener = l;
    }

    public void setResources(String[] names, int nameNomalColor, int nameSelectColors, int[] selectIcon, int[] normalIcon) {
        if (names == null) return;
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            int select = selectIcon[i];
            int normal = normalIcon[i];
            addButton(i, name, nameNomalColor, nameSelectColors, select, normal);
        }
    }

    private void addButton(int i, String name, int normalColor, int selectColor, int selectIcon
            , int normalIcon) {
        ImageButtonView button = new ImageButtonView(mContext);
        button.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT
                , 1));
        button.setName(name);
        button.setNameColor(selectColor, normalColor);
        button.setTag(i);
        button.setSelectImageView(selectIcon, normalIcon);
        addClickListener(button);
        addView(button);
    }

    public void addButton(int tag, String name, int defIcon, int selectIcon) {
        ImageButtonView button = new ImageButtonView(mContext);
        button.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT
                , 1));
        button.setName(name);
        button.setNameColor(textSelectColor, textDefColor);
        button.setTag(tag);
        button.setSelectImageView(selectIcon, defIcon);
        addClickListener(button);
        addView(button);
    }

    private void addClickListener(View view) {
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        if (lastTag == tag) return;
        lastTag = tag;
        if (mOnClickButtonListener == null) {
            return;
        }
        int childSize = getChildCount();
        for (int i = 0; i < childSize; i++) {
            ImageButtonView view = (ImageButtonView) getChildAt(i);
            int viewTag = (int) view.getTag();
            view.setStatus(viewTag == tag);
        }
        mOnClickButtonListener.onClickSelectButton(tag);
    }

    public void setTextColor(int defColor, int selectColor) {
        textDefColor = defColor;
        textSelectColor = selectColor;
    }

    public void setSelectButtonTag(int tag) {
        int childSize = getChildCount();
        for (int i = 0; i < childSize; i++) {
            ImageButtonView view = (ImageButtonView) getChildAt(i);
            int viewTag = (int) view.getTag();
            view.setStatus(tag == viewTag);
        }
        mOnClickButtonListener.onClickSelectButton(tag);
    }

    public interface OnClickButtonListener {
        void onClickSelectButton(int tag);
    }
}
