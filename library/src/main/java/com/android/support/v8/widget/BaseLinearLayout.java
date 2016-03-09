package com.android.support.v8.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author jonatan.salas
 */
public class BaseLinearLayout extends LinearLayout {

    /**
     *
     * @param context
     */
    public BaseLinearLayout(Context context) {
        super(context);
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public BaseLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     *
     */
    protected void updateLayout() {
        invalidate();
        requestLayout();
    }

    /**
     *
     * @param resId
     * @return
     */
    protected Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    /**
     *
     * @param resId
     * @return
     */
    protected int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }
}
