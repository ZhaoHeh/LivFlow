package com.zhaoheh.livflow;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ContainSwipeView extends LinearLayout {

    private static final String TAG = "ContainSwipeView";


    /** 我们之所以重载三个构造函数, 是因为ContainSwipeView在不同情境下实例化时所需的参数不同
     * 如果仅实现一个构造函数会出现不可预料的错误, 常见的如下:
     * Binary XML file line Error #2:Error inflating class com.zhaoheh.livflow.ContainSwipeView
     */
    public ContainSwipeView(Context context) {
        super(context);
    }


    public ContainSwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public ContainSwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**ContainSwipeView的主要目的是加入日志输出功能, 帮助我理解事件分发机制,
     * 因此对三个与时间分发有关的方法除了加入日志打印外, 其他保持默认
     */


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


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent Enter:");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onInterceptTouchEvent ACTION_DOWN Enter:");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent ACTION_MOVE Enter:");
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onInterceptTouchEvent ACTION_UP Enter:");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onTouchEvent Enter:");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent ACTION_DOWN Enter:");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent ACTION_MOVE Enter:");
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent ACTION_UP Enter:");
                break;
        }
        return super.onTouchEvent(ev);
    }
}
