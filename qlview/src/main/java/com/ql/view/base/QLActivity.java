package com.ql.view.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ql.view.bind.BindViewUtils;
import com.ql.view.utils.ActivityUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by mrqiu on 2017/7/15.
 */

public abstract class QLActivity extends AppCompatActivity implements View.OnClickListener {
    private static final long MIN_CLICK_DOUBLE = 300; //过滤 连续点击
    private static final String INTENT_BUNDLE = "bundle";


    private long mLastClickTime;//记录上次单击时间
    private QLFragment currentKJFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        createView();
        ActivityUtils.getInstance().addAty(this);
        int color = getStatusBarTintColor();
        if (color != 0) {
            //设置沉浸式布局时需要给layout布局设置android:fitsSystemWindows="true"该属性
            //设置状态栏颜色
            //第三方控件
            //github:https://github.com/jgilfelt/SystemBarTint
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true);
            }

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            //为所有系统栏设置自定义颜

            tintManager.setTintColor(color);
        }

        BindViewUtils.find(this);
        initData();
        initView();
    }

    public int getStatusBarTintColor() {
        return 0;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    public void toBack() {
        finish();
    }


    public void initView() {
    }

    public void initData() {
    }


    /**
     * 用于扫描二维码后的回调
     *
     * @param resultCode
     * @param code
     */
    public void onScanningResult(int resultCode, String code) {

    }

    @Override
    public void onClick(View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - mLastClickTime > MIN_CLICK_DOUBLE) {
            mLastClickTime = clickTime;
            clickWidget(v);
        }

    }


    public void clickWidget(View v) {

    }

    protected abstract void createView();

    public void startActivity(Class aty) {
        Intent intent = new Intent(this, aty);
        startActivity(intent);
    }

    public void startActivity(String atyName){
        Intent intent = new Intent();
        intent.setClassName(this,atyName);
        startActivity(intent);
    }

    public void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtra(INTENT_BUNDLE, bundle);
        startActivity(intent);
    }

    public Bundle getBundle() {
        Intent intent = getIntent();
        if (intent == null) {
            return null;
        }
        return intent.getBundleExtra(INTENT_BUNDLE);
    }

    public void startActivity(Class clazz, int requestCode) {
        startActivity(clazz, null, requestCode);
    }

    public void startActivity(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(INTENT_BUNDLE, bundle);
        startActivityForResult(intent, requestCode);
    }
    /*********************************************************************/
    /*********************************************************************/
    /********************
     * 用于隐藏软键盘
     **********************************/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /********************************************************************/

    public void changeFragment(int fm_content, QLFragment targetFragment) {
        if (targetFragment == null) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(fm_content, targetFragment, targetFragment.getClass().getName());
        }
        if (currentKJFragment != null && currentKJFragment.isAdded() && !currentKJFragment.isHidden()) {
            transaction.hide(currentKJFragment);
        }
        currentKJFragment = targetFragment;

        transaction.show(targetFragment).commit();
    }

    @Override
    protected void onDestroy() {
        ActivityUtils.getInstance().removeAty(this);
        super.onDestroy();
    }
}
