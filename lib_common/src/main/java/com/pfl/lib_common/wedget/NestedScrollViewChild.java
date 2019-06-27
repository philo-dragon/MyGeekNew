package com.pfl.lib_common.wedget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

public class NestedScrollViewChild extends WebView implements NestedScrollingChild2 {

    private final int[] mScrollConsumed = new int[2];
    private final int[] mScrollOffset = new int[2];
    private NestedScrollingChildHelper mChildHelper;

    public NestedScrollViewChild(Context context) {
        this(context, null);
    }

    public NestedScrollViewChild(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollViewChild(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mChildHelper = new NestedScrollingChildHelper(this);
        mChildHelper.setNestedScrollingEnabled(true);
    }

    private int mFirstY;
    private int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) event.getRawY();
                mFirstY = mLastY;
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) event.getRawY();
                int dy = y - mLastY;
                mLastY = y;
                if (mChildHelper.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL) &&
                        mChildHelper.dispatchNestedPreScroll(0, -dy, mScrollConsumed, mScrollOffset)) {
                    //父View已处理滑动
                    int slid = -dy - mScrollConsumed[1];
                    scrollBy(0, slid);
                } else {
                    //需要子View自己处理滑动
                    scrollBy(0, -dy);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return super.onTouchEvent(event);
    }

    /************  NestedScrollingChild2  *************/

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return mChildHelper.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        mChildHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return mChildHelper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }
}
