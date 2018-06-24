package com.zhaoheh.livflow;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SwipeView extends ViewGroup {

    private static final String TAG = "SwipeView";

    // 在OnIntercept阶段使用到的变量
    private final int mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();


    // 在OnTouchEvent阶段用到的变量
    private int downX, moved;


    private Scroller mScroller = new Scroller(getContext());


    // 两个标志着右侧参看是否滑出的变量
    private boolean haveShowRight = false;

    /** staticSwipeView 域被设定为static
     * 那么 JVM 在 SwipeView 类初始化阶段会在方法区的静态部分为其申请一块内存空间
     * 在 SwipeView 类的实例化时, 对于这些静态类变量不会在堆区申请内存
     * 因此, SwipeView 类的全部实例将共享一个 staticSwipeView 域
     */
    public static SwipeView staticSwipeView;


    public SwipeView(Context context) {
        super(context);
    }


    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (staticSwipeView != null) {
            staticSwipeView.closeMenu();
            staticSwipeView = null;
        }
    }


    public void closeMenus() {
        smoothScrollTo(0, 0);
        haveShowRight = false;
        staticSwipeView = null;
    }


    public static void closeMenu() {
        if (staticSwipeView != null) {
            staticSwipeView.closeMenus();
        }
    }


    /**缓慢滚动到指定位置, getScrollX()会获取x轴方向上的偏移量
     */
    private void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        // 若干ms内滑动destX，效果就是慢慢滑动
        mScroller.startScroll(scrollX, 0, delta, 0, 100);
        invalidate();
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent Enter:");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "dispatchTouchEvent ACTION_DOWN Enter:");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "dispatchTouchEvent ACTION_MOVE Enter:");
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "dispatchTouchEvent ACTION_UP Enter:");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    // TODO: 搞明白为什么使用原生onInterceptTouchEvent方法如下, 滑动事件就不被响应
    // TODO: 但是使用第二个onInterceptTouchEvent方法, 不论拦截条件成立时返回真或假, 都可以响应滑动事件


    /**现在我试着解释上面的问题:
     *
     * 按照原生onInterceptTouchEvent()的逻辑, MOVE事件一定会下发, 本View将没有机会直接处理MOVE事件
     *
     * 同时, 下级InSwipeView收到MOVE事件后, 如果处理逻辑如下:
     * ret = Math.abs(downX - (int) ev.getX()) < ViewConfiguration.get(getContext()).getScaledTouchSlop()
     * 那么意味着, 如果是无效滑动距离, 它会拦截并消费, 但因为代码中InSwipeView只设定了点击和长按监听,
     * 因此InSwipeView会将MOVE事件忽略, 将滑动操作当做点击操作处理
     *
     * 如果滑动距离有效, 那么InSwipeView会下发, 由于子View中都没有响应滑动的监听, 那么最后会冒泡上传,
     * 但是, 由于一次操作中的第一个MOVE事件往往都是无效滑动距离, 因此会被InSwipeView拦截, 此后本次操作中的所有滑动事件都会被拦截
     * 于是本View再也无法响应滑动事件
     *
     * 根据上面的解释预测现象应该是:
     * 使用原生onInterceptTouchEvent(), 界面将无法响应滑动事件, 所有滑动事件都将会在InSwipeView中当做点击事件处理
     */


    /**按照下面代码的逻辑, MOVE事件会有条件地下发, 若滑动距离有效则拦截并消费, 若滑动距离无效则下发
     * 因此, 一次操作中前几次MOVE事件将有机会下发, 但之后的一系列有效滑动的MOVE事件将会被拦截并消费, 也就会被看做一次点击操作
     *
     * 如果InSwipeView的处理逻辑如下:
     * ret = Math.abs(downX - (int) ev.getX()) < ViewConfiguration.get(getContext()).getScaledTouchSlop()
     * 那么InSwipeView会对下发的无效滑动的MOVE事件拦截并消费, 对于永远也不可能下发的MOVE事件, InSwipeView也永远不会拦截
     * 所以看出来InSwipeView的逻辑和SwipeView的逻辑有重复的地方
     *
     * 根据上面的推测, 我产生一个疑问:
     * TODO 当InSwipeView需要响应点击事件同时SwipeView需要响应滑动事件时, 系统为什么把此次操作交给了SwipeView去响应滑动?
     * 同时我产生了一个预测: 将InSwipeView的onInterceptTouchEvent()改为原生逻辑, 效果和
     * ret = Math.abs(downX - (int) ev.getX()) < ViewConfiguration.get(getContext()).getScaledTouchSlop()
     * 一样
     */

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent Enter:");
        boolean ret = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onInterceptTouchEvent ACTION_DOWN Enter:");
                downX = (int) ev.getX();
                ret = false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent ACTION_MOVE Enter:");
                /**如果MOVE事件的移动距离大于一个基准值, 即达到"有效滑动", 就令返回值为true,
                 * 也就不再下发MOVE事件, 而交给本View的onTouchEvent()处理
                 */
                ret = Math.abs(downX - (int) ev.getX()) > mScaledTouchSlop;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onInterceptTouchEvent ACTION_UP Enter:");
                ret = false;
                break;
        }
        return ret;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onTouchEvent Enter:");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent ACTION_DOWN Enter:");
                downX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent ACTION_MOVE Enter:");

                // 如果滑动还未完成, 则不再响应本次MOVE事件
                if (!mScroller.isFinished())
                    break;

                // 加了这段代码之后没什么影响
                if (staticSwipeView != null && staticSwipeView == this && haveShowRight) {
                    closeMenu();
                    break;
                }

                moved = (int) ev.getX() - downX;

                if (moved >= 0)
                    smoothScrollTo(0, 0);
                else if (moved < 0 && moved > -20)
                    smoothScrollTo(-moved, 0);
                else if (moved <= -20 && !haveShowRight)
                    smoothScrollTo(getChildAt(1).getMeasuredWidth(), 0);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent ACTION_UP Enter:");

                if (staticSwipeView != null) {
                    closeMenu();
                }

                if (getScrollX() >= getChildAt(1).getMeasuredWidth() / 2) {
                    smoothScrollTo(getChildAt(1).getMeasuredWidth(), 0);
                    staticSwipeView = this;
                    haveShowRight = true;
                } else {
                    smoothScrollTo(0, 0);
                    haveShowRight = false;
                }
                break;
        }
        return true;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        View child = getChildAt(0);
        int margin =
                ((MarginLayoutParams) child.getLayoutParams()).topMargin
                        + ((MarginLayoutParams) child.getLayoutParams()).bottomMargin;
        setMeasuredDimension(width, child.getMeasuredHeight() + margin);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (i == 0)
                child.layout(l, t, r, b);
            else if (i >= 1)
                child.layout(r, t, r + child.getMeasuredWidth(), b);

        }
    }

}
