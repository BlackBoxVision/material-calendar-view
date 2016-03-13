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
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.android.support.calendar.R;
import com.android.support.calendar.model.DayTime;
import com.android.support.calendar.model.Event;
import com.android.support.calendar.util.CalendarUtility;

/**
 * This class is a calendar widget for displaying dates, selecting, adding and associating event for a
 * specific day.
 *
 * @author jonatan.salas
 */
public class CalendarView extends LinearLayout {
    private View view;

    private Calendar mCurrentCalendar;

    @Nullable
    private OnMonthChangeListener mOnMonthChangeListener;

    @Nullable
    private OnDateSelectedListener mOnDateSelectedListener;

//    private Boolean mHeaderViewEnabled = true;
//    private Boolean mWeekendEnabled = true;
//    private Boolean mHolidayEnabled = true;

//    //Variable for custom typeface
//    private Typeface mTypeface;

    //General background color
    private int mCalendarViewBackgroundColor;

    //HeaderView background and text color + font size
    private int mHeaderViewBackgroundColor;
    private int mHeaderViewTextColor;
    private float mHeaderViewFontSize;

    //WeekView background and text color + font size
    private int mWeekViewBackgroundColor;
    private int mWeekViewTextColor;
    private float mWeekViewFontSize;

    //AdapterView background and text color + font size
    private int mAdapterViewBackgroundColor;
    private int mAdapterViewTextColor;
    private float mAdapterViewFontSize;

    //Disable days background and text color variables
    private int mDisabledBackgroundColor;
    private int mDisabledTextColor;

//    //Selected day background and text color variables
//    private int mSelectedBackgroundColor;
//    private int mSelectedTextColor;

    //Weekend day background and text color variables
//    private int mWeekendBackgroundColor;
    private int mWeekendTextColor;

//    //Holiday background and text color variables
//    private int mHolidayBackgroundColor;
//    private int mHolidayTextColor;

    //Current day background and text color
    private int mCurrentBackgroundColor;
    private int mCurrentTextColor;

    //ImageButton drawable..
    private Drawable mLeftArrowDrawable;
    private Drawable mRightArrowDrawable;

    //Drawable color..
    private int mDrawableColor;
    private int mCurrentMonthIndex = 0;

    private AdapterView adapterView;
    private HeaderView headerView;
    private View weekView;

    /**
     * The callback used to indicate the user changes the date.
     *
     * @author jonatan.salas
     */
    public interface OnDateSelectedListener {

        /**
         * Called upon change of the selected day.
         *
         * @param view The view associated with this listener.
         * @param year The year that was set.
         * @param month The month that was set [0-11].
         * @param dayOfMonth The day of the month that was set.
         * @param eventList The list of events associated to this date selected.
         */
        void onDateSelected(@NonNull View view, int year, int month, int dayOfMonth, @Nullable List<Event> eventList);
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
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        final int white = ContextCompat.getColor(getContext(), R.color.white);
        final int colorPrimary = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        final int colorAccent = ContextCompat.getColor(getContext(), R.color.colorAccent);
        final int darkerGray = ContextCompat.getColor(getContext(), android.R.color.darker_gray);

        final float titleFontSize = 15f;
        final float fontSize = 14f;

        try {
//            mHeaderViewEnabled = a.getBoolean(R.styleable.CalendarView_headerViewEnable, true);
//            mWeekendEnabled = a.getBoolean(R.styleable.CalendarView_weekendEnable, true);
//            mHolidayEnabled = a.getBoolean(R.styleable.CalendarView_holidayEnable, true);

            //Font size values..
            mHeaderViewFontSize = a.getFloat(R.styleable.CalendarView_headerViewFontSize, titleFontSize);
            mWeekViewFontSize = a.getFloat(R.styleable.CalendarView_weekViewFontSize, titleFontSize);
            mAdapterViewFontSize = a.getFloat(R.styleable.CalendarView_adapterViewFontSize, fontSize);

            //Background color values..
            mCalendarViewBackgroundColor = a.getColor(R.styleable.CalendarView_calendarViewBackgroundColor, colorPrimary);
            mHeaderViewBackgroundColor = a.getColor(R.styleable.CalendarView_headerViewBackgroundColor, colorPrimary);
            mWeekViewBackgroundColor = a.getColor(R.styleable.CalendarView_weekViewBackgroundColor, colorPrimary);
            mAdapterViewBackgroundColor = a.getColor(R.styleable.CalendarView_adapterViewBackgroundColor, colorPrimary);
            mDisabledBackgroundColor = a.getColor(R.styleable.CalendarView_disabledBackgroundColor, colorPrimary);
//            mSelectedBackgroundColor = a.getColor(R.styleable.CalendarView_selectedBackgroundColor, colorAccent);
//            mWeekendBackgroundColor = a.getColor(R.styleable.CalendarView_weekViewBackgroundColor, colorPrimary);
//            mHolidayBackgroundColor = a.getColor(R.styleable.CalendarView_holidayBackgroundColor, colorPrimary);
            mCurrentBackgroundColor = a.getColor(R.styleable.CalendarView_currentBackgroundColor, colorAccent);

            //Text Color values..
            mHeaderViewTextColor = a.getColor(R.styleable.CalendarView_headerViewTextColor, colorAccent);
            mWeekViewTextColor = a.getColor(R.styleable.CalendarView_weekViewTextColor, white);
            mAdapterViewTextColor = a.getColor(R.styleable.CalendarView_adapterViewTextColor, white);
            mDisabledTextColor = a.getColor(R.styleable.CalendarView_disabledTextColor, darkerGray);
//            mSelectedTextColor = a.getColor(R.styleable.CalendarView_selectedTextColor, white);
            mWeekendTextColor = a.getColor(R.styleable.CalendarView_weekendTextColor, colorAccent);
//            mHolidayTextColor = a.getColor(R.styleable.CalendarView_holidayTextColor, colorAccent);
            mCurrentTextColor = a.getColor(R.styleable.CalendarView_currentTextColor, white);

            //Drawable Color values..
            mDrawableColor = a.getColor(R.styleable.CalendarView_drawableColor, colorAccent);

            //Arrow drawables..
            mLeftArrowDrawable = a.getDrawable(R.styleable.CalendarView_leftArrowDrawable);
            mRightArrowDrawable = a.getDrawable(R.styleable.CalendarView_rightArrowDrawable);

        } finally {
            a.recycle();
        }
    }

    private void init() {
        mCurrentCalendar = Calendar.getInstance(Locale.getDefault());

        view = LayoutInflater.from(getContext()).inflate(R.layout.calendar_view, this, true);
        view.setBackgroundColor(mCalendarViewBackgroundColor);

        initHeaderView();
        initWeekView();
        initAdapterView();
    }

    private void initHeaderView() {
        headerView = (HeaderView) view.findViewById(R.id.calendar_title_layout);

        headerView.setBackgroundColor(mHeaderViewBackgroundColor);
//        headerView.setTitleTextColor(mHeaderViewTextColor);
        headerView.setTitleTextSize(mHeaderViewFontSize);
        headerView.setTitleTextTypeface(Typeface.DEFAULT);
//        headerView.setNextButtonDrawableColor(mDrawableColor);
//        headerView.setBackButtonDrawableColor(mDrawableColor);

        headerView.setOnButtonClicked(new HeaderView.OnButtonClickedListener() {
            @Override
            public void onButtonClicked(@NonNull View view, int monthIndex) {
                final int id = view.getId();

                if (id == R.id.back_button) {
                    mCurrentMonthIndex--;
                } else if (id == R.id.next_button) {
                    mCurrentMonthIndex++;
                }

                if (null != mOnMonthChangeListener) {
                    mCurrentCalendar.add(Calendar.MONTH, mCurrentMonthIndex);

                    final int month = mCurrentCalendar.get(Calendar.MONTH);
                    final int year = mCurrentCalendar.get(Calendar.YEAR);

                    adapterView.updateMonthsAdapter(mCurrentCalendar, monthIndex);
                    mOnMonthChangeListener.onMonthChanged(view, year, month);
                }
            }
        });
    }

    private void initWeekView() {
        weekView = view.findViewById(R.id.calendar_week_view);
        weekView.setBackgroundColor(mWeekViewBackgroundColor);

        final String[] shortWeekDays = CalendarUtility.getShortWeekDays();
        TextView dayOfWeek;
        String dayName;

        for (int i = 1; i < shortWeekDays.length; i++) {
            dayName = shortWeekDays[i].trim().toUpperCase(Locale.getDefault());
            int length = (dayName.length() < 3) ? dayName.length() : 3;
            dayName = dayName.substring(0, length).toUpperCase(Locale.getDefault());

            final int intTag = CalendarUtility.calculateWeekIndex(i, mCurrentCalendar);
            final String tag = String.valueOf(intTag);

            dayOfWeek = (TextView) weekView.findViewWithTag(tag);
            dayOfWeek.setText(dayName);
            dayOfWeek.setTextColor(mWeekViewTextColor);
            dayOfWeek.setTextSize(mWeekViewFontSize);
        }
    }

    private void initAdapterView() {
        adapterView = (AdapterView) view.findViewById(R.id.calendar_adapter_view);

        adapterView.updateMonthsAdapter(mCurrentCalendar, mCurrentMonthIndex);
        adapterView.setOnListItemSelectedListener(new AdapterView.OnListItemSelectedListener() {
            @Override
            public void onItemSelected(@NonNull View view, @NonNull DayTime dayTime) {
                if (null != mOnDateSelectedListener) {
                    mOnDateSelectedListener.onDateSelected(
                            view,
                            dayTime.getYear(),
                            dayTime.getMonth(),
                            dayTime.getDay(),
                            dayTime.getEventList()
                    );
                }
            }
        });
    }

    public void setOnMonthChangeListener(@Nullable OnMonthChangeListener onMonthChangeListener) {
        this.mOnMonthChangeListener = onMonthChangeListener;
    }

    public void setOnDateSelectedListener(@Nullable OnDateSelectedListener onDateSelectedListener) {
        this.mOnDateSelectedListener = onDateSelectedListener;
    }
}