/*
 * Copyright (C) 2015 Jonisaa {link: http://the-android-developer.blogspot.com.ar}.
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
package com.android.support.v8.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.android.support.v8.R;

/**
 * This class is a calendar widget for displaying and selecting dates.
 *
 * @author jonatan.salas
 */
public class CalendarView extends FrameLayout {
    private static final int SPAN_COUNT = 7;
    private static final int SIZE = 42;

    protected LayoutInflater mInflater;
    protected RecyclerView mRecyclerView;
    protected Context mContext;

    //View containers..
    protected View mRootView;
    protected View mHeaderView;
    protected View mWeekView;

    protected Calendar mCurrentCalendar;
    private Boolean mScrollEnabled = true;

    @Nullable
    private OnMonthChangeListener mOnMonthChangeListener;

    @Nullable
    private OnDateChangeListener mOnDateChangeListener;

    /**
     * The callback used to indicate the user changes the date.
     *
     * @author jonatan.salas
     */
    public interface OnDateChangeListener {

        /**
         * Called upon change of the selected day.
         *
         * @param view The view associated with this listener.
         * @param year The year that was set.
         * @param month The month that was set [0-11].
         * @param dayOfMonth The day of the month that was set.
         */
        void onDateChanged(@NonNull View view, int year, int month, int dayOfMonth);
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
        super(context, null, 0);
        saveValues(context);
        init();
    }

    /**
     * Constructor with params. It takes the context as main param, and an AttributeSet as second
     * param. We use the context to get the resources that needs to inflate correctly and the AttributeSet
     * object used to style the view after inflate it.
     *
     * @param context The application context used to get needed resources.
     * @param attrs The AttributeSet used to get custom styles and apply to this view.
     */
    public CalendarView(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs, 0);
        saveValues(context);
        style(attrs);
        init();

        //Do this in order to render..
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode()) {
                return;
            }
        }
    }

    /**
     * Protected method used to get some attributes.
     *
     * @param context the context object used to get the attributes.
     */
    protected void saveValues(@NonNull Context context) {
        mInflater = obtainLayoutInflater(context);
        mCurrentCalendar = obtainCalendar();
        mContext = context;
    }

    /**
     * Protected method used to style the view from AttributeSet object.
     *
     * @param attrs AttributeSet object with custom values to be applied.
     */
    protected void style(@NonNull AttributeSet attrs) {
        final TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            //TODO JS: Obtain values here..
            mScrollEnabled = a.getBoolean(R.styleable.CalendarView_isScrollEnabled, true);
        } finally {
            a.recycle();
        }
    }

    /**
     *
     */
    protected void init() {
        mRootView = mInflater.inflate(R.layout.calendar_view, this, true);

        //Init CalendarView parts..
        initHeaderView();
        initWeekView();
        initAdapterView();
    }

    /**
     *
     */
    protected void initHeaderView() {
        mHeaderView = mRootView.findViewById(R.id.header_view);
        final TextView dateTitle = (TextView) findViewById(R.id.date_title);
        dateTitle.setText(getDateTitle());
        mHeaderView.invalidate();
    }

    /**
     *
     */
    protected void initWeekView() {
        mWeekView = mRootView.findViewById(R.id.week_view);
        String dayName;
        TextView dayOfWeek;

        for(int i = 1; i < getShortWeekDays().length; i++) {
            dayName = getShortWeekDay(i);
            int length = (dayName.length() < 3)? dayName.length() : 3;
            dayName = dayName.substring(0, length).toUpperCase();

            dayOfWeek = (TextView) findViewWithTag(String.valueOf(calculateWeekIndex(i, getCurrentCalendar())));
            dayOfWeek.setText(dayName);
        }

        mWeekView.invalidate();
    }

    /**
     *
     */
    protected void initAdapterView() {
        final List<Day> days = obtainDays(obtainMonthDisplayHelper(), getCurrentDay());
        final DayAdapter dayAdapter = new DayAdapter(mContext, days);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(obtainGridLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(dayAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(mScrollEnabled);

        dayAdapter.notifyDataSetChanged();

        mRecyclerView.invalidate();
    }

    /**
     * POJO class, representing a Day with properties needed to style the view.
     *
     * @author jonatan.salas
     */
    protected class Day {
        private int day;
        private boolean isCurrentDay;
        private boolean isThisMonth;
        private boolean isWeekend;

        /**
         * Constructor with params.
         *
         * @param day int representing a Day of Month
         * @param isThisMonth boolean value, used to verify if the day is or not in this month.
         * @param isCurrentDay boolean value, used to verify if the day is the current.
         * @param isWeekend boolean value, used to verify if the day is at weekend.
         */
        public Day(int day, boolean isThisMonth, boolean isCurrentDay, boolean isWeekend) {
            this.day = day;
            this.isThisMonth = isThisMonth;
            this.isCurrentDay = isCurrentDay;
            this.isWeekend = isWeekend;
        }

        /**
         * Constructor with params. It sets by default all boolean params to false.
         *
         * @param day int representing a Day of Month
         */
        public Day(int day) {
            this(day, false, false, false);
        }

        public int getDay() {
            return day;
        }

        public boolean isCurrentDay() {
            return isCurrentDay;
        }

        public boolean isThisMonth() {
            return isThisMonth;
        }

        public boolean isWeekend() {
            return isWeekend;
        }
    }

    /**
     * @author jonatan.salas
     */
    protected class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
        protected List<Day> mItems;
        protected Context mContext;

        /**
         * @author jonatan.salas
         */
        protected class DayViewHolder extends RecyclerView.ViewHolder {
            protected TextView mDayView;

            /**
             *
             * @param view
             */
            public DayViewHolder(@NonNull View view) {
                super(view);
                mDayView = (TextView) view.findViewById(R.id.day_view);
            }
        }

        /**
         *
         * @param context
         * @param items
         */
        public DayAdapter(@NonNull Context context, @NonNull List<Day> items) {
            mContext = context;
            mItems = items;
        }

        @Override
        public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View v = mInflater.inflate(R.layout.day_view, parent, false);
            return new DayViewHolder(v);
        }

        @Override
        public void onBindViewHolder(DayViewHolder holder, int position) {
            final Day day = mItems.get(position);
            holder.mDayView.setClickable(true);
            holder.mDayView.setText(String.valueOf(day.getDay()));

            if (!day.isThisMonth()) {
                holder.mDayView.setTextColor(Color.BLUE);
                holder.mDayView.setEnabled(false);
                holder.mDayView.setClickable(false);
            }

            if(day.isWeekend()) {
                holder.mDayView.setTextColor(Color.RED);
            }

            if(day.isCurrentDay()) {
                holder.mDayView.setBackgroundColor(Color.BLUE);
                holder.mDayView.setTextColor(Color.WHITE);
            }
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        /**
         * Method using to set a List of days in the adapter.
         *
         * @param items the list of days to show.
         */
        public void setItems(List<Day> items) {
            this.mItems = items;
        }
    }

    //-----------------------------------------------------------------//
    //                         PROTECTED GETTERS                       //
    //-----------------------------------------------------------------//
    /***
     *
     * @param weekIndex
     * @param calendar
     * @return
     */
    protected int calculateWeekIndex(int weekIndex, Calendar calendar) {
        int firstDayWeekPosition = calendar.getFirstDayOfWeek();
        if (firstDayWeekPosition == 1) {
            return weekIndex;
        } else {
            if (weekIndex == 1) {
                return 7;
            } else {
                return weekIndex - 1;
            }
        }
    }

    /**
     *
     * @param helper
     * @param currentDay
     * @return
     */
    protected List<Day> obtainDays(@NonNull MonthDisplayHelper helper, int currentDay) {
        List<Day> days = new ArrayList<>(SIZE);

        for(int i = 0; i < 6; i++) {
            int n[] = helper.getDigitsForRow(i);

            for(int d = 0; d < 7; d++) {
                if (helper.isWithinCurrentMonth(i, d)) {
                    Calendar calendar = obtainCalendar();
                    calendar.set(Calendar.DAY_OF_MONTH, n[d]);

                    if(n[d] == currentDay && isWeekend(calendar)) {
                        days.add(new Day(n[d], true, true, true));
                    } else if(isWeekend(calendar)) {
                        days.add(new Day(n[d], true, false, true));
                    } else {
                        days.add(new Day(n[d], true, false, false));
                    }
                } else {
                    days.add(new Day(n[d]));
                }
            }
        }

        return days;
    }

    /**
     *
     * @return
     */
    protected MonthDisplayHelper obtainMonthDisplayHelper() {
        return new MonthDisplayHelper(getYear(), getMonth(), getFirstDayOfWeek());
    }

    /**
     *
     * @return
     */
    protected GridLayoutManager obtainGridLayoutManager() {
        return new GridLayoutManager(mContext, SPAN_COUNT);
    }

    /**
     *
     * @param context
     * @return
     */
    protected LayoutInflater obtainLayoutInflater(@NonNull Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     *
     * @return
     */
    protected Calendar obtainCalendar() {
        return Calendar.getInstance(Locale.getDefault());
    }

    /**
     *
     * @param calendar
     * @return
     */
    protected boolean isWeekend(Calendar calendar) {
        int position = calendar.get(Calendar.DAY_OF_WEEK);
        return position == Calendar.SATURDAY || position == Calendar.SUNDAY;
    }

    //TODO JS: HERE GOES SETTERS AND GETTERS. DOCUMENT THEM..
    //-----------------------------------------------------------------//
    //                            SETTERS                              //
    //-----------------------------------------------------------------//
    public void setOnMonthChangeListener(@Nullable OnMonthChangeListener onMonthChangeListener) {
        this.mOnMonthChangeListener = onMonthChangeListener;
    }

    public void setOnDateChangeListener(@Nullable OnDateChangeListener onDateChangeListener) {
        this.mOnDateChangeListener = onDateChangeListener;
    }

    public void setScrollEnabled(Boolean scrollEnabled) {
        this.mScrollEnabled = scrollEnabled;
    }

    //-----------------------------------------------------------------//
    //                            GETTERS                              //
    //-----------------------------------------------------------------//
    public OnMonthChangeListener getOnMonthChangeListener() {
        return mOnMonthChangeListener;
    }

    public OnDateChangeListener getOnDateChangeListener() {
        return mOnDateChangeListener;
    }

    public Boolean isScrollEnabled() {
        return mScrollEnabled;
    }

    public int getYear() {
        return mCurrentCalendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return mCurrentCalendar.get(Calendar.MONTH);
    }

    public int getFirstDayOfWeek() {
        return mCurrentCalendar.getFirstDayOfWeek();
    }

    public int getCurrentDay() {
        return mCurrentCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getShortWeekDay(int position) {
        return getShortWeekDays()[position].trim().toUpperCase(Locale.getDefault());
    }

    public String[] getShortWeekDays() {
        return new DateFormatSymbols(Locale.getDefault()).getShortWeekdays();
    }

    public String getDateTitle() {
        final String month = new DateFormatSymbols(Locale.getDefault()).getMonths()[getMonth()];
        return month.toUpperCase(Locale.getDefault()) + " " + String.valueOf(getYear());
    }

    public Calendar getCurrentCalendar() {
        return mCurrentCalendar;
    }
}