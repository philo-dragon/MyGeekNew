package com.pfl.lib_common.wedget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.TintableBackgroundView;
import com.pfl.lib_common.R;


/**
 * 简介:带清除功能的EditText
 */
public class ClearEditText extends AppCompatEditText implements TintableBackgroundView {

    /**
     * 清除图片
     */
    private Drawable mDrawableClear;

    /**
     * 跟随焦点
     */
    private boolean mFollowFocus;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化
     */
    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClearEditTextStyle);
        if (typedArray == null) {
            return;
        }
        //取图片
        int drawable = typedArray.getResourceId(R.styleable.ClearEditTextStyle_drawable_clear, R.drawable.ic_clear_black_24dp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawableClear = getContext().getDrawable(drawable);
        } else {
            mDrawableClear = getResources().getDrawable(drawable);
        }
        if (mDrawableClear == null) {
            return;
        }
        mDrawableClear.mutate();

        //取着色
        int tint = typedArray.getColor(R.styleable.ClearEditTextStyle_drawable_tint, -1);
        ColorStateList colorStateList = tint == -1 ? getTextColors() : ColorStateList.valueOf(tint);
        DrawableCompat.setTintList(mDrawableClear, colorStateList);
        //取是否跟随焦点
        mFollowFocus = typedArray.getBoolean(R.styleable.ClearEditTextStyle_drawable_follow_focus, false);

        typedArray.recycle();

        checkDrawableVisible();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        checkDrawableVisible();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        checkDrawableVisible();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = getCompoundDrawables()[2];
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight()) && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 判断是否显示图片
     */
    private void checkDrawableVisible() {
        if (mFollowFocus) {
            setDrawableVisible(hasFocus() && length() > 0);
        } else {
            setDrawableVisible(length() > 0);
        }
    }

    /**
     * 设置图片显示状态
     */
    private void setDrawableVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1]
                , visible ? mDrawableClear : null, getCompoundDrawables()[3]);
    }

}