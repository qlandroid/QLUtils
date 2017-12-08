package com.ql.utils.qlutils.move;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ql.view.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2017/12/6
 * 描述:可以对 模块进行移动
 *
 * @author ql
 */

public class CanMoveSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = "CanMoveSurfaceView";
    private boolean running;
    private int[] colors = {Color.RED, Color.YELLOW, Color.DKGRAY, Color.LTGRAY};
    private int colorIndex;
    private SurfaceHolder mSurfaceHolder;
    private Thread mDrawThread;

    private OperateParams mOperateParams;

    private MoveParams mMoveParams;
    private ScaleParams mScaleParams;

    private Paint paint;

    private ScaleGestureDetector mScaleGestureDetector;//缩放事件
    private GestureDetector mGestureDetector;//双击事件

    public CanMoveSurfaceView(Context context) {
        super(context);
        init();
    }

    public CanMoveSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanMoveSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        //设置Surface生命周期回调
        mSurfaceHolder.addCallback(this);
        mDrawThread = new Thread(this);
        mOperateParams = new OperateParams();
        mScaleParams = new ScaleParams();

        setFocusableInTouchMode(true);

        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        mGestureDetector = new GestureDetector(getContext(), new ZoomGesture());
        paint = new Paint();

        mMoveParams = new MoveParams();
    }

    private float lastX, lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        int pointerCount = event.getPointerCount();
        Log.i(TAG, "onTouchEvent: 手指的数量 -->" + pointerCount);
        mScaleGestureDetector.onTouchEvent(event);


        //多指头操作
        //单指操作
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Module pointInModuleArea = getPointInModuleArea(event.getX(), event.getY());
                if (pointInModuleArea != null) {
                    if (colorIndex >= colors.length) {
                        colorIndex = 0;
                    }
                    pointInModuleArea.setColor(colors[colorIndex]);
                    colorIndex++;
                }
                mScaleParams.setOperateScale(false);
                //第一个手指
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX() - lastX;
                float moveY = event.getY() - lastY;
                lastX = event.getX();
                lastY = event.getY();

                if (mScaleParams.isOperateScale()) {
                    //当前是双指操作
                    if (pointerCount >= 2) {
                        PointF middle = mScaleParams.middle(event);
                        mScaleParams.setMiddle(middle);
                        if (middle != null && mScaleParams.isOperateScale() && (mScaleParams.getScaleFactor() > 0.00004)) {
                            if (!mScaleParams.isScaleChange()) {
                                break;
                            }
                            float moveX_center = middle.x - middle.x * mScaleParams.getScaleFactor();
                            float moveY_center = middle.y - middle.y * mScaleParams.getScaleFactor();
                            mMoveParams.addMove(moveX_center, moveY_center);
                            //每次计算完毕后， 记录这次操作的放大因数，解决当手指不动时，继续计算
                            mScaleParams.setLastScale(mScaleParams.getScale());
                        }
                    }

                } else if (!mScaleParams.isOperateScale()) {
                    mMoveParams.addMove(moveX, moveY);
                    Log.i(TAG, "onTouchEvent: moveX " + mMoveParams.getMoveX() + ", moveY = " + mMoveParams.getMoveY());
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //第二个手指出现;
                double distance = mScaleParams.distance(event);
                if (distance > 10f) {
                    Log.i(TAG, "onTouchEvent: 第二手指， 可以获得到 两个手指的中点坐标");
                    PointF middle = mScaleParams.middle(event);
                    mScaleParams.setMiddle(middle);
                    mScaleParams.setOperateScale(true);
                }
                break;
            default:
        }


        mScaleParams.setMiddle(null);

        return true;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setRunning(true);
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        setRunning(false);
        try {
            mDrawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        Canvas c = null;

        while (isRunning()) {

            try {
                synchronized (CanMoveSurfaceView.class) {

                    c = mSurfaceHolder.lockCanvas();
                    doDraw(c);
                    //通过它来控制帧数执行一次绘制后休息50ms
                    Thread.sleep(50);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (c != null) {
                    mSurfaceHolder.unlockCanvasAndPost(c);
                }
            }

        }
    }

    /**
     * 绘制信息
     *
     * @param canvas
     */
    private void doDraw(Canvas canvas) throws Exception {
        if (canvas == null) {
            return;
        }
        canvas.drawColor(Color.WHITE);
        String nowScale = String.format("当前放大倍数 %.6f", mScaleParams.getScale());
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        canvas.drawText(nowScale, 70, 70, paint);

        List<Module> modules = mOperateParams.getModules();
        if (modules == null) {
            return;
        }
        for (Module module : modules) {
            float x = getXScaleAndMoveAfter(module.getX());
            float endX = getXScaleAndMoveAfter(module.getEndX());
            float y = getYScaleAndMoveAfter(module.getY());
            float endY = getYScaleAndMoveAfter(module.getEndY());

            paint.setColor(module.getColor());
            if (paint.getStyle() != Paint.Style.FILL) {
                paint.setStyle(Paint.Style.FILL);
            }
            canvas.drawRect(x, y, endX, endY, paint);
        }
    }

    /**
     * 获得点击模块
     *
     * @param x 当前屏幕点击的  y 点
     * @param y 当前屏幕点击的 y 点
     * @return
     */
    private Module getPointInModuleArea(float x, float y) {
        Log.i(TAG, "getPointInModuleArea:相对屏幕上的点 click point = " + x + "," + y);
        float clickX = (x - mMoveParams.getMoveX()) / mScaleParams.getScale();
        float clickY = (y - mMoveParams.getMoveY()) / mScaleParams.getScale();
        Log.i(TAG, "getPointInModuleArea:相对原点 click point = " + clickX + "," + clickY);
        List<Module> modules = mOperateParams.getModules();
        for (Module module : modules) {
            if ((clickX > module.getX())
                    && (clickX < module.getEndX())
                    && (clickY > module.getY())
                    && (clickY < module.getEndY())) {
                //在该模块的矩形区域中
                return module;
            }
        }
        return null;

    }

    /**
     * 纵坐标上，经过放大，移动 后的 虚拟坐标。
     *
     * @param y
     * @return
     */
    private float getYScaleAndMoveAfter(int y) {
        return y * mScaleParams.getScale() + mMoveParams.getMoveY();
    }

    /**
     * 横坐标上，经过放大，移动 后的 虚拟坐标。
     *
     * @param x
     * @return
     */
    private float getXScaleAndMoveAfter(float x) {
        return x * mScaleParams.getScale() + mMoveParams.getMoveX();
    }

    public void addModules(Module module) {
        List<Module> modules = mOperateParams.getModules();
        if (modules == null) {
            modules = new ArrayList<>();
        }
        if (!isCanAddModule(module)) {
            ToastUtils.show(getContext(), "不可添加");
            return;
        }

        modules.add(module);
        mOperateParams.setModules(modules);
    }

    public boolean isCanAddModule(Module addModule) {
        List<Module> modules = mOperateParams.getModules();
        if (modules == null || modules.size() == 0) {
            return true;
        }

        int addModuleX = addModule.getX();
        int addModuleEndX = addModule.getEndX();
        int addModuleY = addModule.getY();
        int addModuleEndY = addModule.getEndY();
        for (Module module : modules) {
            int x = module.getX();
            int endX = module.getEndX();
            int y = module.getY();
            int endY = module.getEndY();

            //是否重叠
            if (isScope(addModuleX, addModuleEndX, addModuleY, addModuleEndY, x, endX, y, endY)) {
                return false;
            }


        }

        return true;

    }

    /**
     * 用于判断两个模块 是否重合
     *
     * @param addModuleX
     * @param addModuleEndX
     * @param addModuleY
     * @param addModuleEndY
     * @param x
     * @param endX
     * @param y
     * @param endY
     * @return
     */
    private boolean isScope(int addModuleX, int addModuleEndX, int addModuleY, int addModuleEndY, int x, int endX, int y, int endY) {
        return !((endX <= addModuleX) || (y >= addModuleEndY) || (x > addModuleEndX) || (endY < addModuleY));
    }

    public void setModules(List<Module> modules) {
        mOperateParams.setModules(modules);
    }

    public void setRunning(boolean isRunning) {
        this.running = isRunning;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        detector.getCurrentSpan();//两点间的距离跨度
        detector.getCurrentSpanX();//两点间的x距离
        detector.getCurrentSpanY();//两点间的y距离
        detector.getFocusX();       //
        detector.getFocusY();       //
        detector.getPreviousSpan(); //上次
        detector.getPreviousSpanX();//上次
        detector.getPreviousSpanY();//上次
        detector.getEventTime();    //当前事件的时间
        detector.getTimeDelta();    //两次事件间的时间差
        if (!mScaleParams.isOperateScale()) {
            return false;
        }
        float scaleFactor = detector.getScaleFactor();//与上次事件相比，得到的比例因子
        Log.i(TAG, "onScale: " + scaleFactor);
        float scale = mScaleParams.getScale();
        float newScale = scale * scaleFactor;
        if (newScale > ScaleParams.SCALE_MAX) {
            newScale = ScaleParams.SCALE_MAX;
            scaleFactor = newScale / scale;
        } else if (newScale < ScaleParams.SCALE_MIN) {
            newScale = ScaleParams.SCALE_MIN;
            scaleFactor = newScale / scale;
        }

        mScaleParams.setScale(newScale);
        mScaleParams.setScaleFactor(scaleFactor);

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }


    class ZoomGesture extends GestureDetector.SimpleOnGestureListener {//单手指操作

        @Override //双击
        public boolean onDoubleTap(MotionEvent e) {
            System.out.println("--onDoubleTap---");
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            System.out.println("--onDoubleTapEvent---");
            return super.onDoubleTapEvent(e);
        }
    }

}
