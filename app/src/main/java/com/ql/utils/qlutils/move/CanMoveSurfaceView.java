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

public class CanMoveSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, ScaleGestureDetector.OnScaleGestureListener, IOperate {
    private static final String TAG = "CanMoveSurfaceView";
    private boolean running;
    private int[] colors = {Color.RED, Color.YELLOW, Color.DKGRAY, Color.LTGRAY};
    private int colorIndex;
    private SurfaceHolder mSurfaceHolder;
    private Thread mDrawThread;

    private OperateParams mOperateParams;

    private MoveParams mMoveParams;
    private ScaleParams mScaleParams;
    private Box mBox;
    private BoxOperate mBoxOperate;

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
        mBox = new Box();
        mBoxOperate = new BoxOperate();
        setFocusableInTouchMode(true);

        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        mGestureDetector = new GestureDetector(getContext(), new ZoomGesture());
        paint = new Paint();

        mMoveParams = new MoveParams();
    }

    private float lastX, lastY;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        mBox.setEndX(sizeWidth - 100);
    }

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
                mScaleParams.setOperateScale(false);
                //第一个手指
                lastX = event.getX();
                lastY = event.getY();
                if (mOperateParams.getMoveStatus() == MoveStatus.MOVE_MODULE) {
                    Module pointInModuleArea = getPointInModuleArea(lastX, lastY);
                    if (pointInModuleArea != null) {
                        Module module = new Module(pointInModuleArea);
                        mBox.setMoveToBoxY(0);
                        mBox.setMoveToBoxX(0);
                        mBox.setMoveModule(module);
                        mOperateParams.getModules().remove(pointInModuleArea);
                    }
                    mOperateParams.setOperateModule(pointInModuleArea);

                }
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX() - lastX;
                float moveY = event.getY() - lastY;
                lastX = event.getX();
                lastY = event.getY();
                if (mOperateParams.getMoveStatus() == MoveStatus.SCALE) {
                    toScaleOperate(event);
                    toMoveOperate(moveX, moveY);
                } else if (mOperateParams.getMoveStatus() == MoveStatus.MOVE_MODULE) {

                    moveModuleFromBox();

                    float moveToBoxX = mBox.getMoveToBoxX();
                    moveToBoxX += moveX;
                    mBox.setMoveToBoxX(moveToBoxX);
                    float moveToBoxY = mBox.getMoveToBoxY();
                    moveToBoxY += moveY;
                    mBox.setMoveToBoxY(moveToBoxY);
                }
                break;
            case MotionEvent.ACTION_UP:
                Module operateModule = mBox.getMoveModule();
                if (mOperateParams.getMoveStatus() == MoveStatus.MOVE_MODULE && operateModule != null) {
                    //判断当前抬起后，点的区域是否在 Box 内;
                    float upX = event.getX();
                    float upY = event.getY();
                    if (mBox.getHideModuleIndex() != Box.HIDEL_MODULE_CLEAR) {

                        operateMoveModule(event);


                    } else if (mBoxOperate.isScope(upX, upY, mBox)) {
                        //当前移动的 module 是否 在当前盒子的范围区域内
                        mBoxOperate.addModule(upX, upY, mOperateParams.getOperateModule(), mBox);
                        mOperateParams.setOperateModule(null);
                        mBox.setMoveModule(null);
                    } else {
                        //让 module 返回到初始位置
                        operateModule.setDivWidth(0);
                        mOperateParams.getModules().add(operateModule);
                        mBox.setMoveModule(null);
                    }
                }
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

    /**
     * 当前移动module中，进行的操作
     */
    private void moveModuleFromBox() {
        List<Module> boxSaveModule = mBox.getBoxSaveModule();
        boolean isClickModuleArea = false;//是否点击到盒子中的模型状态
        Module clickModule = null;//点击的medule
        int clickModuleIndex = -1;//点击盒子中module中的角标
        for (int i = 0; i < boxSaveModule.size(); i++) {
            Module module = boxSaveModule.get(i);
            if (mBoxOperate.isScope(lastX, lastY, module)) {
                isClickModuleArea = true;
                clickModule = module;
                clickModuleIndex = i;
                break;
            }
        }
        if (isClickModuleArea) {
            List<Module> srcModule = mBox.getSrcModule();
            Module module = srcModule.get(clickModuleIndex);
            //获得 当前比例下的 宽度，高度
            Module moduleMoveOut = new Module(module);
            float scaleWidth = moduleMoveOut.getWidth() * mScaleParams.getScale();
            float scaleHeight = moduleMoveOut.getHeight() * mScaleParams.getScale();

            moduleMoveOut.setHeight(scaleHeight);
            moduleMoveOut.setWidth(scaleWidth);
            float moveOutX = (clickModule.getX() + clickModule.getEndX()) / 2 - scaleWidth / 2;
            float moveOutEndX = moveOutX + scaleWidth;

            float moveOutY = (clickModule.getY() + clickModule.getEndY()) / 2 - scaleHeight / 2;
            float moveOutEndY = moveOutY + scaleHeight;
            moduleMoveOut.setX(moveOutX);
            moduleMoveOut.setEndX(moveOutEndX);
            moduleMoveOut.setY(moveOutY);
            moduleMoveOut.setEndY(moveOutEndY);
            mBox.setMoveModule(moduleMoveOut);

            mBox.setHideModuleIndex(clickModuleIndex);
        }
    }

    /**
     * 当前为松手后 移动module操作,控制位置
     * 条件，松手位置需要判断 是否与当前 在坐标系中module 重合，
     * 不重合：删除盒子中的 移动的 module,并在 当前坐标系中添加 当前module
     * 重合:  做提示操作,并 返回到盒子中，让盒子中隐藏的module显示出来，并移除当前移动的module
     */
    private void operateMoveModule(MotionEvent event) {
        float upX = event.getX();
        float upY = event.getY();
        if (mBoxOperate.isScope(upX, upY, mBox)) {
            mBox.clearHideModuleIndex();
            mBox.setMoveModule(null);
            mBox.setMoveToBoxY(0f);
            mBox.setMoveToBoxX(0f);
            return;
        }

        //当前在从 盒子中移除 module 操作;
        Module moveModule = mBox.getMoveModule();
        Module srcMoveModule = new Module(moveModule);
        srcMoveModule.setX(getSrcX(moveModule.getX()));
        srcMoveModule.setEndX(getSrcX(moveModule.getEndX()));
        srcMoveModule.setY(getSrcY(moveModule.getY()));
        srcMoveModule.setEndY(getSrcY(moveModule.getY()));
        if ((isCanAddModule(srcMoveModule)) || mOperateParams.isOpenCoverage()) {
            //不重合 或没有开启 重合位置不可添加
            mBoxOperate.removeModule(mBox.getHideModuleIndex(), mBox);
            mBox.clearHideModuleIndex();
            List<Module> modules = mOperateParams.getModules();
            modules.add(srcMoveModule);

        } else {
            //当前重合状态
            mBox.clearHideModuleIndex();
            mBox.setMoveModule(null);
        }

    }

    /**
     * 进行移动操作
     *
     * @param moveX 横向 偏移量
     * @param moveY 纵向 偏移量
     */
    private void toMoveOperate(float moveX, float moveY) {
        mMoveParams.addMove(moveX, moveY);
    }

    /**
     * 进行缩放操作时，要以 两指中间点 为中心 进行缩放操作，相当于将屏幕进行偏移，使屏幕可以以该中心
     * 进行缩放操作,
     *
     * @param event
     */
    private void toScaleOperate(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        //当前是双指操作
        if (pointerCount >= 2) {

            PointF middle = mScaleParams.middle(event);
            mScaleParams.setMiddle(middle);
            if (middle != null && mScaleParams.isOperateScale() && (mScaleParams.getScaleFactor() > 0.00004)) {
                if (!mScaleParams.isScaleChange()) {
                    return;
                }
                //计算当前 偏移量
                float moveX_center = middle.x - middle.x * mScaleParams.getScaleFactor();
                float moveY_center = middle.y - middle.y * mScaleParams.getScaleFactor();
                //解决以两指 为中心点进行缩放操作
                toMoveOperate(moveX_center, moveY_center);
                //每次计算完毕后， 记录这次操作的放大因数，解决当手指不动时，继续计算
                mScaleParams.setLastScale(mScaleParams.getScale());
            }
        }
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


        //绘制模块
        for (Module module : modules) {
            drawModule(canvas, module);
        }


        /**
         * 当前需要将 显示一个收纳盒， 主要负责 收集需要移动的模块，用于临时存储，
         * 主要目的是临时存储，将模块位置改变
         */
        if (mOperateParams.getMoveStatus() == MoveStatus.MOVE_MODULE) {
            drawBox(canvas);

            Module operateModule = mBox.getMoveModule();
            if (operateModule != null) {
                drawModuleMoveToBox(canvas, operateModule, mBox);
            }
        }
    }

    private void drawBox(Canvas canvas) {
        //绘制盒子 的背景
        int divWidth = mBox.getDivWidth();
        if (divWidth > 0) {
            if (paint.getColor() != mBox.getDivColor()) {
                paint.setColor(mBox.getDivColor());
            }
            canvas.drawRect(mBox.getX() - divWidth, mBox.getY() - divWidth, mBox.getEndX() + divWidth, mBox.getEndY() + divWidth, paint);
        }
        if (paint.getColor() != mBox.getBgColor()) {
            paint.setColor(mBox.getBgColor());
        }
        canvas.drawRect(mBox.getX(), mBox.getY(), mBox.getEndX(), mBox.getEndY(), paint);

        //绘制盒子内的模块
        List<Module> boxSaveModule = mBox.getBoxSaveModule();
        for (int i = 0; i < boxSaveModule.size(); i++) {
            Module module = boxSaveModule.get(i);
            if (mBox.getHideModuleIndex() == i) {
                continue;
            }

            drawModuleSrc(canvas, module);
        }

    }

    private void drawModuleSrc(Canvas canvas, Module module) {
        float x = module.getX();
        float endX = module.getEndX();
        float y = module.getY();
        float endY = module.getEndY();


        int divWidth = module.getDivWidth();
        if (divWidth > 0) {
            paint.setColor(module.getDivColor());
            canvas.drawRect(x - divWidth, y - divWidth, endX + divWidth, endY + divWidth, paint);
        }

        paint.setColor(module.getColor());
        if (paint.getStyle() != Paint.Style.FILL) {
            paint.setStyle(Paint.Style.FILL);
        }
        canvas.drawRect(x, y, endX, endY, paint);
    }

    /**
     * 绘制当前移动的module 模块
     *
     * @param canvas
     * @param module
     * @param box
     */
    private void drawModuleMoveToBox(Canvas canvas, Module module, Box box) {
        float x = getXScaleAndMoveAfter(module.getX()) + box.getMoveToBoxX();
        float endX = getXScaleAndMoveAfter(module.getEndX()) + box.getMoveToBoxX();
        float y = getYScaleAndMoveAfter(module.getY()) + box.getMoveToBoxY();
        float endY = getYScaleAndMoveAfter(module.getEndY()) + box.getMoveToBoxY();


        int divWidth = module.getDivWidth();
        if (divWidth > 0) {
            paint.setColor(module.getDivColor());
            canvas.drawRect(x - divWidth, y - divWidth, endX + divWidth, endY + divWidth, paint);
        }

        paint.setColor(module.getColor());
        if (paint.getStyle() != Paint.Style.FILL) {
            paint.setStyle(Paint.Style.FILL);
        }
        canvas.drawRect(x, y, endX, endY, paint);
    }

    /**
     * 绘制 模块
     *
     * @param canvas
     * @param module 需要绘制的模块
     */
    private void drawModule(Canvas canvas, Module module) {
        float x = getXScaleAndMoveAfter(module.getX());
        float endX = getXScaleAndMoveAfter(module.getEndX());
        float y = getYScaleAndMoveAfter(module.getY());
        float endY = getYScaleAndMoveAfter(module.getEndY());


        int divWidth = module.getDivWidth();
        if (divWidth > 0) {
            paint.setColor(module.getDivColor());
            canvas.drawRect(x - divWidth, y - divWidth, endX + divWidth, endY + divWidth, paint);
        }

        paint.setColor(module.getColor());
        if (paint.getStyle() != Paint.Style.FILL) {
            paint.setStyle(Paint.Style.FILL);
        }
        canvas.drawRect(x, y, endX, endY, paint);
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
    private float getYScaleAndMoveAfter(float y) {
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

    private float getSrcX(float x) {
        return (x - mMoveParams.getMoveX()) / mScaleParams.getScale();
    }

    private float getSrcY(float y) {
        return (y - mMoveParams.getMoveY()) / mScaleParams.getScale();
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

        float addModuleX = addModule.getX();
        float addModuleEndX = addModule.getEndX();
        float addModuleY = addModule.getY();
        float addModuleEndY = addModule.getEndY();
        for (Module module : modules) {
            float x = module.getX();
            float endX = module.getEndX();
            float y = module.getY();
            float endY = module.getEndY();

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
    private boolean isScope(float addModuleX, float addModuleEndX, float addModuleY, float addModuleEndY, float x, float endX, float y, float endY) {
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
        if ((!mScaleParams.isOperateScale()) && (mOperateParams.getMoveStatus() != MoveStatus.SCALE)) {
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

    @Override
    public void setStatus(int status) {
        mOperateParams.setMoveStatus(status);
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
