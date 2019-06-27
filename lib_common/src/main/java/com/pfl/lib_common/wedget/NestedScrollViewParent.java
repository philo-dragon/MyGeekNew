package com.pfl.lib_common.wedget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;

public class NestedScrollViewParent extends LinearLayout implements NestedScrollingParent2 {

    private NestedScrollingParentHelper mParentHelper;

    private int slip = 200;
    private int mScreenWidth;
    private NestedScrollViewChild mNestedScrollViewChild;

    public NestedScrollViewParent(Context context) {
        this(context, null);
    }

    public NestedScrollViewParent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollViewParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        slip = (int) (200 * getResources().getDisplayMetrics().density + 0.5f);
        mParentHelper = new NestedScrollingParentHelper(this);
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);

        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = measureWidth;
        } else {
            width = mScreenWidth;
        }

        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams params = child.getLayoutParams();
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, left + right, params.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, top + bottom, params.height);
            measureChild(child, childWidthMeasureSpec, childHeightMeasureSpec);
        }
        setMeasuredDimension(width, measureHeight);
        findNestedScrollView(this);
    }

    private void findNestedScrollView(ViewGroup parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof NestedScrollViewChild) {
                mNestedScrollViewChild = (NestedScrollViewChild) child;
                break;
            }
            if (child instanceof ViewGroup) {
                findNestedScrollView((ViewGroup) child);
            }
        }
    }

    private int mLastMotionY;
    private int mLastY;
    private boolean mIsBeingDragged;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = (int) ev.getY();
                mIsBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 拦截落在不可滑动子View的MOVE事件
                final int y = (int) ev.getY();
                final int yDiff = Math.abs(y - mLastMotionY);
                boolean isInNestedChildViewArea = isTouchNestedInnerView((int) ev.getRawX(), (int) ev.getRawY());
                if (!isInNestedChildViewArea) {
                    mIsBeingDragged = true;
                    mLastMotionY = y;
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                break;
        }

        return mIsBeingDragged;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mLastY == 0) {
                    mLastY = (int) event.getY();
                    return true;
                }
                int y = (int) event.getY();
                int dy = y - mLastY;
                mLastY = y;
                scrollBy(0, -dy);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mLastY = 0;
                break;
        }
        return true;
    }

    private boolean isTouchNestedInnerView(int x, int y) {

        List<View> innerView = new ArrayList<>();
        if (mNestedScrollViewChild != null) {
            innerView.add(mNestedScrollViewChild);
        }
        for (int i = 0; i < getChildCount(); i++) {
            View nestedView = getChildAt(i);
            if (nestedView.getVisibility() != View.VISIBLE) {
                continue;
            }
            int[] location = new int[2];
            nestedView.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];
            int right = left + nestedView.getMeasuredWidth();
            int bottom = top + nestedView.getMeasuredHeight();
            if (y >= top && y <= bottom && x >= left && x <= right) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > slip) {
            y = slip;
        }
        super.scrollTo(x, y);
    }

    /************  NestedScrollingParent2  *************/

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mParentHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mParentHelper.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        println("onNestedScroll, dyConsumed = " + dyConsumed);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        println("onNestedPreScroll,  dy = " + dy);
        if (dy > 0) {//向上滑动
            if (getScrollY() < slip) {
                scrollBy(0, dy * 2 / 3);
                consumed[1] = dy * 1 / 3;
            }
        } else {//向下滑动
            if (getScrollY() > 0) {
                scrollBy(0, dy * 2 / 3);
                consumed[1] = dy * 1 / 3;
            }
        }
    }

    public void println(String msg) {
        Log.e(getClass().getSimpleName(), msg);
    }
}
