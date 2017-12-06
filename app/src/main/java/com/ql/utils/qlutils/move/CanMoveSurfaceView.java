package com.ql.utils.qlutils.move;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

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

    private SurfaceHolder mSurfaceHolder;
    private Thread mDrawThread;

    private OperateParams mOperateParams;

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

        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        mGestureDetector = new GestureDetector(getContext(), new ZoomGesture());
        paint = new Paint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        if (mScaleGestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
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

                    c = mSurfaceHolder.lockCanvas(null);
                    doDraw(c);
                    //通过它来控制帧数执行一次绘制后休息50ms
                    Thread.sleep(50);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mSurfaceHolder.unlockCanvasAndPost(c);
            }

        }
    }

    /**
     * 绘制信息
     *
     * @param canvas
     */
    private void doDraw(Canvas canvas) throws Exception {
        canvas.drawColor(Color.WHITE);

        List<Module> modules = mOperateParams.getModules();
        if (modules == null) {
            return;
        }
        for (Module module : modules) {
            int scale = mOperateParams.getScale();
            int x = module.getX() * scale;
            int endX = module.getEndX() * scale;
            int y = module.getY() * scale;
            int endY = module.getEndY() * scale;

            paint.setColor(module.getColor());
            if (paint.getStyle() != Paint.Style.FILL) {
                paint.setStyle(Paint.Style.FILL);
            }
            canvas.drawRect(x, y, endX, endY, paint);
        }
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
        detector.getEventTime();    //当前事件的事件
        detector.getTimeDelta();    //两次事件间的时间差
        float scaleFactor = detector.getScaleFactor();//与上次事件相比，得到的比例因子
        Log.i(TAG, "onScale: " + scaleFactor);


        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
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
