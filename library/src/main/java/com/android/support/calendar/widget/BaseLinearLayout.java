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
 * Extended LinearLayout class that we are going to use in all
 * the CalendarView related classes.
 *
 * @author jonatan.salas
 */
public class BaseLinearLayout extends LinearLayout {

    /**
     * Constructor with arguments. It only takes the Context as param.
     *
     * @param context the context used to inflate or get resources
     */
    public BaseLinearLayout(Context context) {
        this(context, null, 0);
    }

    /**
     * Constructor with arguments. It takes the Context as main param
     * and as second param an AttributeSet.
     *
     * @param context the context used to inflate or get resources
     * @param attrs the attributes styled from a XML file
     */
    public BaseLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor with arguments. It takes the Context as main param
     * and as second param an AttributeSet, and as third param the defStyleAttr.
     *
     * @param context the context used to inflate or get resources
     * @param attrs the attributes styled from a XML file
     * @param defStyleAttr int resource used to get the Styles array
     */
    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Method that invalidates a View, and request a layout change after layout edition or a custom
     * attribute modification.
     */
    protected void updateLayout() {
        invalidate();
        requestLayout();
    }

    /**
     * Method that gets a Drawable object by its resource id.
     *
     * @param resId int value representing the resource id of the drawable to get.
     * @return a Drawable object ready to use.
     */
    protected Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    /**
     * Method that gets an int color representation by its resource id.
     *
     * @param resId int value that represents the resource id of the color to get.
     * @return an int value that represents a color.
     */
    protected int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }
}
