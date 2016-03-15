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
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.android.support.calendar.adapter.DayTimeAdapter;
import com.android.support.calendar.R;
import com.android.support.calendar.model.DayTime;
import com.android.support.calendar.model.Event;
import com.android.support.calendar.util.CalendarUtility;
import com.android.support.calendar.util.ScreenUtility;

import static com.android.support.calendar.R.styleable.*;

/**
 * This class is a calendar widget for displaying dates, selecting, adding and associating event for a
 * specific day.
 *
 * @author jonatan.salas
 */
public class CalendarView extends LinearLayout {
    private static final Interpolator DEFAULT_ANIM_INTERPOLATOR = new DecelerateInterpolator(3.0f);
    private static final long DEFAULT_ANIM_DURATION = 1500;
    private static final int DEFAULT_MONTH_INDEX = 0;
    
    private AdapterView adapterView;
    private HeaderView headerView;
    private View weekView;
    private View view;

    private Calendar calendar;

    @Nullable
    private OnMonthChangeListener onMonthChangeListener;

    @Nullable
    private OnDayTimeClickListener onDayTimeClickListener;

    @Nullable
    private OnDayTimeLongClickListener onDayTimeLongClickListener;

    @Nullable
    private OnDayTimeStyleChangeListener onDayTimeStyleChangeListener;

    private Integer calendarViewBackgroundColor;

    private Integer headerViewBackgroundColor;
    private Integer headerViewTextColor;
    private Float headerViewFontSize;
    private Integer weekViewBackgroundColor;
    private Integer weekViewTextColor;
    private Float weekViewFontSize;
    private Integer adapterViewBackgroundColor;
    private Integer adapterViewTextColor;
    private Float adapterViewFontSize;
    private Integer disabledBackgroundColor;
    private Integer disabledTextColor;
    private Integer weekendTextColor;
    private Integer currentBackgroundColor;
    private Integer currentTextColor;

    private Drawable leftArrowDrawable;
    private Drawable rightArrowDrawable;

    private Integer drawableColor;
    
    /**
     * Constructor with params. It takes the context as param, and use to get the
     * resources that needs to inflate correctly. It requires a non null context object.
     *
     * @param context The application context used to get needed resources.
     */
    public CalendarView(@NonNull Context context) {
        this(context, null, 0);
    }

    /**
     * Constructor with params. It takes the context as main param, and an AttributeSet as second
     * param. We use the context to get the resources that needs to inflate correctly and the AttributeSet
     * object used to style the view after inflate it.
     *
     * @param context The application context used to get needed resources.
     * @param attrs The AttributeSet used to get custom styles and apply to this view.
     */
    public CalendarView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor with params. It takes the context as main param, and an AttributeSet as second
     * param. We use the context to get the resources that needs to inflate correctly and the AttributeSet
     * object used to style the view after inflate it.
     *
     * @param context The application context used to get needed resources.
     * @param attrs The AttributeSet used to get custom styles and apply to this view.
     * @param defStyle Style definition for this View
     */
    public CalendarView(@NonNull Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        style(attrs);
        init();
    }

    private void style(AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, CalendarView);

        final int white = ContextCompat.getColor(getContext(), R.color.white);
        final int prim = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        final int accent = ContextCompat.getColor(getContext(), R.color.colorAccent);
        final int darkerGray = ContextCompat.getColor(getContext(), android.R.color.darker_gray);

        final float titleFontSize = 15f;
        final float fontSize = 14f;

        try {
            //Font size values..
            headerViewFontSize = a.getFloat(CalendarView_headerViewFontSize, titleFontSize);
            weekViewFontSize = a.getFloat(CalendarView_weekViewFontSize, titleFontSize);
            adapterViewFontSize = a.getFloat(CalendarView_adapterViewFontSize, fontSize);

            //Background color values..
            calendarViewBackgroundColor = a.getColor(CalendarView_calendarViewBackgroundColor, prim);
            headerViewBackgroundColor = a.getColor(CalendarView_headerViewBackgroundColor, prim);
            weekViewBackgroundColor = a.getColor(CalendarView_weekViewBackgroundColor, prim);
            adapterViewBackgroundColor = a.getColor(CalendarView_adapterViewBackgroundColor, prim);
            disabledBackgroundColor = a.getColor(CalendarView_disabledBackgroundColor, prim);
            currentBackgroundColor = a.getColor(CalendarView_currentBackgroundColor, accent);

            //Text Color values..
            headerViewTextColor = a.getColor(CalendarView_headerViewTextColor, accent);
            weekViewTextColor = a.getColor(CalendarView_weekViewTextColor, white);
            adapterViewTextColor = a.getColor(CalendarView_adapterViewTextColor, white);
            disabledTextColor = a.getColor(CalendarView_disabledTextColor, darkerGray);
            weekendTextColor = a.getColor(CalendarView_weekendTextColor, accent);
            currentTextColor = a.getColor(CalendarView_currentTextColor, white);

            //Drawable Color values..
            drawableColor = a.getColor(CalendarView_drawableColor, accent);

            //Arrow drawables..
            leftArrowDrawable = a.getDrawable(CalendarView_leftArrowDrawable);
            rightArrowDrawable = a.getDrawable(CalendarView_rightArrowDrawable);

        } finally {
            a.recycle();
        }
    }

    private void init() {
        calendar = Calendar.getInstance(Locale.getDefault());

        view = LayoutInflater.from(getContext()).inflate(R.layout.calendar_view, this, true);
        view.setBackgroundColor(calendarViewBackgroundColor);

        initHeaderView();
        initWeekView();
        initAdapterView();
    }

    private void initHeaderView() {
        headerView = (HeaderView) view.findViewById(R.id.calendar_title_layout);

        headerView.setBackgroundColor(headerViewBackgroundColor);
        headerView.setTitleTextColor(headerViewTextColor);
        headerView.setTitleTextSize(headerViewFontSize);
        headerView.setTitleTextTypeface(Typeface.DEFAULT);
        headerView.setNextButtonDrawableColor(drawableColor);
        headerView.setBackButtonDrawableColor(drawableColor);

        headerView.setOnButtonClicked(new HeaderView.OnButtonClickedListener() {
            @Override
            public void onButtonClicked(@NonNull View view, int monthIndex) {
                if (null != onMonthChangeListener) {
                    final Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.add(Calendar.MONTH, monthIndex);

                    final int month = calendar.get(Calendar.MONTH) + 1;
                    final int year = calendar.get(Calendar.YEAR);

                    adapterView.init(calendar, monthIndex);
                    onMonthChangeListener.onMonthChanged(view, year, month);
                }
            }
        });
    }

    private void initWeekView() {
        weekView = view.findViewById(R.id.calendar_week_view);
        weekView.setBackgroundColor(weekViewBackgroundColor);

        final String[] shortWeekDays = CalendarUtility.getShortWeekDays();
        TextView dayOfWeek;
        String dayName;

        for (int i = 1; i < shortWeekDays.length; i++) {
            dayName = shortWeekDays[i].trim().toUpperCase(Locale.getDefault());
            int length = (dayName.length() < 3) ? dayName.length() : 3;
            dayName = dayName.substring(0, length).toUpperCase(Locale.getDefault());

            final int intTag = CalendarUtility.calculateWeekIndex(i, calendar);
            final String tag = String.valueOf(intTag);

            dayOfWeek = (TextView) weekView.findViewWithTag(tag);
            dayOfWeek.setText(dayName);
            dayOfWeek.setTextColor(weekViewTextColor);
            dayOfWeek.setTextSize(weekViewFontSize);
        }
    }

    private void initAdapterView() {
        adapterView = (AdapterView) view.findViewById(R.id.calendar_adapter_view);

        adapterView.getAdapter().setOnStyleChangeListener(new DayTimeAdapter.OnStyleChangeListener() {
            @Override
            public void onStyleChange(@NonNull DayTimeAdapter.DayTimeViewHolder holder, @NonNull DayTime dayTime) {
                if (null != onDayTimeStyleChangeListener) {
                    onDayTimeStyleChangeListener.onDayTimeStyleChange(holder, dayTime);
                }
            }
        });
        adapterView.getAdapter().setOnListItemClickListener(new DayTimeAdapter.OnListItemClickListener() {
            @Override
            public void onListItemClick(@NonNull View view, @NonNull DayTime dayTime) {
                if (null != onDayTimeClickListener) {
                    onDayTimeClickListener.onDayTimeClick(
                            view,
                            dayTime.getYear(),
                            dayTime.getMonth(),
                            dayTime.getDay(),
                            dayTime.getEventList()
                    );
                }
            }
        });
        adapterView.getAdapter().setOnListItemLongClickListener(new DayTimeAdapter.OnListItemLongClickListener() {
            @Override
            public void onListItemLongClick(@NonNull View view, @NonNull DayTime dayTime) {
                if (null != onDayTimeLongClickListener) {
                    onDayTimeLongClickListener.onDayTimeLongClick(
                            view,
                            dayTime.getYear(),
                            dayTime.getMonth(),
                            dayTime.getDay(),
                            dayTime.getEventList()
                    );
                }
            }
        });
        adapterView.init(calendar, DEFAULT_MONTH_INDEX);
    }

    public void shouldAnimateOnEnter(boolean shouldAnimate) {
        shouldAnimateOnEnter(shouldAnimate, DEFAULT_ANIM_DURATION, DEFAULT_ANIM_INTERPOLATOR);
    }

    public void shouldAnimateOnEnter(boolean shouldAnimate, long duration) {
        shouldAnimateOnEnter(shouldAnimate, duration, DEFAULT_ANIM_INTERPOLATOR);
    }

    public void shouldAnimateOnEnter(boolean shouldAnimate, long duration, @NonNull Interpolator interpolator) {
        if (shouldAnimate) {
            ViewCompat.setTranslationY(this, ScreenUtility.getScreenHeight(getContext()));
            ViewCompat.setAlpha(this, 0f);
            ViewCompat.animate(this)
                    .translationY(0f)
                    .setDuration(duration)
                    .alpha(1f)
                    .setInterpolator(interpolator)
                    .start();

            invalidate();
        }
    }

    public void setOnMonthChangeListener(@Nullable OnMonthChangeListener onMonthChangeListener) {
        this.onMonthChangeListener = onMonthChangeListener;
        invalidate();
    }

    public void setOnDayTimeClickListener(@Nullable OnDayTimeClickListener onDayTimeClickListener) {
        this.onDayTimeClickListener = onDayTimeClickListener;
        invalidate();
    }

    public void setOnDayTimeLongClickListener(@Nullable OnDayTimeLongClickListener onDayTimeLongClickListener) {
        this.onDayTimeLongClickListener = onDayTimeLongClickListener;
        invalidate();
    }

    public void setOnDayTimeStyleChangeListener(@Nullable OnDayTimeStyleChangeListener onDayTimeStyleChangeListener) {
        this.onDayTimeStyleChangeListener = onDayTimeStyleChangeListener;
        invalidate();
    }

    /**
     * The callback used to indicate the user changes the date.
     *
     * @author jonatan.salas
     */
    public interface OnDayTimeClickListener {

        /**OnDayTimeLongClickListener
         * Called upon change of the selected day.
         *
         * @param view The view associated with this listener.
         * @param year The year that was set.
         * @param month The month that was set [0-11].
         * @param dayOfMonth The day of the month that was set.
         * @param eventList The list of events associated to this date selected.
         */
        void onDayTimeClick(@NonNull View view, int year, int month, int dayOfMonth, @Nullable List<Event> eventList);
    }

    /**
     * The callback used to indicate the user changes the date when keeping pressed during a time.
     *
     * @author jonatan.salas
     */
    public interface OnDayTimeLongClickListener {

        /**
         * Called upon change of the selected day.
         *
         * @param view The view associated with this listener.
         * @param year The year that was set.
         * @param month The month that was set [0-11].
         * @param dayOfMonth The day of the month that was set.
         * @param eventList The list of events associated to this date selected.
         */
        void onDayTimeLongClick(@NonNull View view, int year, int month, int dayOfMonth, @Nullable List<Event> eventList);
    }

    /**
     *
     */
    public interface OnDayTimeStyleChangeListener {

        /**
         *
         * @param holder
         * @param dayTime
         */
        void onDayTimeStyleChange(DayTimeAdapter.DayTimeViewHolder holder, DayTime dayTime);
    }

    /**
     * The callback used to indicate the user changes the month.
     *
     * @author jonatan.salas
     */
    public interface OnMonthChangeListener {

        /**
         * Called upon change of the current month.
         *
         * @param view The view associated with this listener.
         * @param year The year that was set.
         * @param month The month that was set [0-11].
         */
        void onMonthChanged(@NonNull View view, int year, int month);
    }
}