/*
 * Copyright (C) 2015 Jonatan E. Salas { link: http://the-android-developer.blogspot.com.ar }
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.support.calendar.widget;

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
