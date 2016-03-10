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
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormatSymbols;
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
// * @attr ref R.styleable#CalendarView_scrollEnable
// * @attr ref R.styleable#CalendarView_headerViewEnable
// * @attr ref R.styleable#CalendarView_weekendEnable
// * @attr ref R.styleable#CalendarView_holidayEnable
// * @attr ref R.styleable#CalendarView_calendarViewBackgroundColor
// * @attr ref R.styleable#CalendarView_headerViewBackgroundColor
// * @attr ref R.styleable#CalendarView_headerViewTextColor
// * @attr ref R.styleable#CalendarView_headerViewFontSize
// * @attr ref R.styleable#CalendarView_weekViewBackgroundColor
// * @attr ref R.styleable#CalendarView_weekViewTextColor
// * @attr ref R.styleable#CalendarView_weekViewFontSize
// * @attr ref R.styleable#CalendarView_adapterViewBackgroundColor
// * @attr ref R.styleable#CalendarView_adapterViewTextColor
// * @attr ref R.styleable#CalendarView_adapterViewFontSize
// * @attr ref R.styleable#CalendarView_disabledBackgroundColor
// * @attr ref R.styleable#CalendarView_disabledTextColor
// * @attr ref R.styleable#CalendarView_selectedBackgroundColor
// * @attr ref R.styleable#CalendarView_selectedTextColor
// * @attr ref R.styleable#CalendarView_weekendBackgroundColor
// * @attr ref R.styleable#CalendarView_weekendTextColor
// * @attr ref R.styleable#CalendarView_holidayBackgroundColor
// * @attr ref R.styleable#CalendarView_holidayTextColor
// * @attr ref R.styleable#CalendarView_currentBackgroundColor
// * @attr ref R.styleable#CalendarView_currentTextColor
// * @attr ref R.styleable#CalendarView_drawableColor
// * @attr ref R.styleable#CalendarView_leftArrowDrawable
// * @attr ref R.styleable#CalendarView_rightArrowDrawable
 *
 * @author jonatan.salas
 */
public class CalendarView extends LinearLayout {
    private static final int SPAN_COUNT = 7;

    private LayoutInflater mInflater;
    private Context mContext;

    //View containers..
    private View mRootView;
    private RecyclerView mAdapterView;

    private DayTimeAdapter mDayTimeAdapter;
    private Calendar mCurrentCalendar;

    @Nullable
    private OnMonthChangeListener mOnMonthChangeListener;

    @Nullable
    private OnDateSelectedListener mOnDateSelectedListener;

    private Boolean mScrollEnabled = true;
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

    private TextView mMonthTitleView;
    private ImageView mNextButton;
    private ImageView mBackButton;

    private final OnClickListener mNextButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mCurrentMonthIndex++;
            mCurrentCalendar = Calendar.getInstance(Locale.getDefault());
            mCurrentCalendar.add(Calendar.MONTH, mCurrentMonthIndex);
            mDayTimeAdapter = new DayTimeAdapter(CalendarUtility.obtainDayTimes(mCurrentCalendar, mCurrentMonthIndex));
            mMonthTitleView.setText(getHeaderTitle(mCurrentCalendar));

            if (null != mAdapterView) {
                mAdapterView.setAdapter(null);
                mAdapterView.setAdapter(mDayTimeAdapter);
                mDayTimeAdapter.notifyDataSetChanged();
            }

            if (null != mOnMonthChangeListener) {
                mOnMonthChangeListener.onMonthChanged(mAdapterView, mCurrentCalendar.get(Calendar.YEAR), mCurrentCalendar.get(Calendar.MONTH));
            }
        }
    };

    private final OnClickListener mBackButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mCurrentMonthIndex--;
            mCurrentCalendar = Calendar.getInstance(Locale.getDefault());
            mCurrentCalendar.add(Calendar.MONTH, mCurrentMonthIndex);
            mDayTimeAdapter = new DayTimeAdapter(CalendarUtility.obtainDayTimes(mCurrentCalendar, mCurrentMonthIndex));
            mMonthTitleView.setText(getHeaderTitle(mCurrentCalendar));

            if (null != mAdapterView) {
                mAdapterView.setAdapter(null);
                mAdapterView.setAdapter(mDayTimeAdapter);
                mDayTimeAdapter.notifyDataSetChanged();
            }

            if (null != mOnMonthChangeListener) {
                mOnMonthChangeListener.onMonthChanged(mAdapterView, mCurrentCalendar.get(Calendar.YEAR), mCurrentCalendar.get(Calendar.MONTH));
            }
        }
    };

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
        super(context);

        if (isInEditMode()) {
            return;
        }

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
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        saveValues(context);
        style(context, attrs);
        init();
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
    public CalendarView(@NonNull Context context, @NonNull AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (isInEditMode()) {
            return;
        }

        saveValues(context);
        style(context, attrs);
        init();
    }

    private void saveValues(@NonNull Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCurrentCalendar = Calendar.getInstance(Locale.getDefault());
        mContext = context.getApplicationContext();
    }

    private void style(@NonNull Context context, @NonNull AttributeSet attrs) {
        final TypedArray a = context.getApplicationContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        final int white = ContextCompat.getColor(context.getApplicationContext(), R.color.white);
        final int colorPrimary = ContextCompat.getColor(context.getApplicationContext(), R.color.colorPrimary);
        final int colorAccent = ContextCompat.getColor(context.getApplicationContext(), R.color.colorAccent);
        final int darkerGray = ContextCompat.getColor(context.getApplicationContext(), android.R.color.darker_gray);

        final float titleFontSize = 15f;
        final float fontSize = 14f;

        try {
            //Boolean values..
            mScrollEnabled = a.getBoolean(R.styleable.CalendarView_scrollEnable, true);
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
        //Init CalendarView parts..
        initRootView();
        initHeaderView();
        initWeekView();
        initAdapterView();
    }

    private void initRootView() {
        mRootView = mInflater.inflate(R.layout.calendar_view, this, true);
        mRootView.setBackgroundColor(mCalendarViewBackgroundColor);
    }

    private void initHeaderView() {
        final View headerView = mRootView.findViewById(R.id.header_view);
        headerView.setBackgroundColor(mHeaderViewBackgroundColor);

        mMonthTitleView = (TextView) findViewById(R.id.date_title);
        mMonthTitleView.setText(getHeaderTitle(Calendar.getInstance(Locale.getDefault())));
        mMonthTitleView.setTextColor(mHeaderViewTextColor);
        mMonthTitleView.setTextSize(mHeaderViewFontSize);

        //TODO JS: Add custom typeface..
        mMonthTitleView.setTypeface(Typeface.DEFAULT_BOLD);

        initNextButton();
        initBackButton();
        headerView.invalidate();
    }

    private void initNextButton() {
        mNextButton = (ImageView) findViewById(R.id.next_button);
        mNextButton.setEnabled(true);
        mNextButton.setClickable(true);

        setNextButtonDrawable(mRightArrowDrawable);
        setNextButtonColor(mDrawableColor);

        mNextButton.setOnClickListener(mNextButtonClickListener);
    }

    private void initBackButton() {
        mBackButton = (ImageView) findViewById(R.id.back_button);
        mBackButton.setEnabled(true);
        mBackButton.setClickable(true);

        setBackButtonDrawable(mRightArrowDrawable);
        setBackButtonColor(mDrawableColor);

        mBackButton.setOnClickListener(mBackButtonClickListener);
    }

    private void initWeekView() {
        final View weekView = mRootView.findViewById(R.id.week_view);
        weekView.setBackgroundColor(mWeekViewBackgroundColor);
        String dayName;
        TextView dayOfWeek;

        for (int i = 1; i < getShortWeekDays().length; i++) {
            dayName = getShortWeekDays()[i].trim().toUpperCase(Locale.getDefault());
            int length = (dayName.length() < 3) ? dayName.length() : 3;
            dayName = dayName.substring(0, length).toUpperCase(Locale.getDefault());

            final String tag = String.valueOf(CalendarUtility.calculateWeekIndex(i, getCurrentCalendar()));

            dayOfWeek = (TextView) findViewWithTag(tag);
            dayOfWeek.setText(dayName);
            dayOfWeek.setTextColor(mWeekViewTextColor);
            dayOfWeek.setTextSize(mWeekViewFontSize);
        }

        weekView.invalidate();
    }

    private void initAdapterView() {
        final List<DayTime> dayTimeList = CalendarUtility.obtainDayTimes(mCurrentCalendar, mCurrentMonthIndex);
        mDayTimeAdapter = new DayTimeAdapter(dayTimeList);

        mAdapterView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        mAdapterView.setLayoutManager(new GridLayoutManager(mContext, SPAN_COUNT));
        mAdapterView.setHasFixedSize(true);
        mAdapterView.setAdapter(mDayTimeAdapter);
        mAdapterView.setItemAnimator(new DefaultItemAnimator());
        mAdapterView.setBackgroundColor(mAdapterViewBackgroundColor);

        mDayTimeAdapter.notifyDataSetChanged();

        mAdapterView.invalidate();
    }

    public void setOnMonthChangeListener(@Nullable OnMonthChangeListener onMonthChangeListener) {
        this.mOnMonthChangeListener = onMonthChangeListener;
    }

    public void setOnDateSelectedListener(@Nullable OnDateSelectedListener onDateSelectedListener) {
        this.mOnDateSelectedListener = onDateSelectedListener;
    }

    public void setNextButtonDrawable(@DrawableRes int drawableId) {
        final Drawable drawable = ContextCompat.getDrawable(mContext, drawableId);

        if (null != drawable) {
            mNextButton.setImageDrawable(drawable);
        }
    }

    public void setBackButtonDrawable(@DrawableRes int drawableId) {
        final Drawable drawable = ContextCompat.getDrawable(mContext, drawableId);

        if (null != drawable) {
            mBackButton.setImageDrawable(drawable);
        }
    }

    public void setNextButtonDrawable(@Nullable Drawable drawable) {
        if (null != drawable) {
            mNextButton.setImageDrawable(drawable);
        }
    }

    public void setBackButtonDrawable(@Nullable Drawable drawable) {
        if (null != drawable) {
            mBackButton.setImageDrawable(drawable);
        }
    }

    public void setNextButtonColor(Integer color) {
        if (color != null) {
            mNextButton.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void setBackButtonColor(Integer color) {
        if (color != null) {
            mBackButton.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void setScrollEnabled(Boolean scrollEnabled) {
        this.mScrollEnabled = scrollEnabled;
    }

    public OnMonthChangeListener getOnMonthChangeListener() {
        return mOnMonthChangeListener;
    }

    public OnDateSelectedListener getOnDateSelectedListener() {
        return mOnDateSelectedListener;
    }

    public Boolean isScrollEnabled() {
        return mScrollEnabled;
    }

    private int getYear() {
        return mCurrentCalendar.get(Calendar.YEAR);
    }

    private int getMonth() {
        return mCurrentCalendar.get(Calendar.MONTH);
    }

    private int getFirstDayOfWeek() {
        return mCurrentCalendar.getFirstDayOfWeek();
    }

    public int getCurrentDay() {
        return mCurrentCalendar.get(Calendar.DAY_OF_MONTH);
    }

    private String[] getShortWeekDays() {
        return new DateFormatSymbols(Locale.getDefault()).getShortWeekdays();
    }

    private String getHeaderTitle(Calendar calendar) {
        final String month = new DateFormatSymbols(Locale.getDefault()).getMonths()[calendar.get(Calendar.MONTH)];
        return month.toUpperCase(Locale.getDefault()) + " " + getYear();
    }

    private Calendar getCurrentCalendar() {
        return mCurrentCalendar;
    }

    private class DayTimeAdapter extends RecyclerView.Adapter<DayTimeAdapter.DayTimeViewHolder> {
        private final List<DayTime> mItems;

        protected class DayTimeViewHolder extends RecyclerView.ViewHolder {
            private final TextView mDayView;

            public DayTimeViewHolder(@NonNull View view) {
                super(view);
                this.mDayView = (TextView) view.findViewById(R.id.day_view);
            }
        }

        public DayTimeAdapter(@NonNull List<DayTime> items) {
            this.mItems = items;
        }

        @Override
        public DayTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View v = mInflater.inflate(R.layout.day_view, parent, false);
            return new DayTimeViewHolder(v);
        }

        @Override
        public void onBindViewHolder(DayTimeViewHolder holder, int position) {
            final DayTime dayTime = mItems.get(position);

            holder.mDayView.setClickable(true);
            holder.mDayView.setText(String.valueOf(dayTime.getDay()));
            holder.mDayView.setTextSize(mAdapterViewFontSize);
            holder.mDayView.setTextColor(mAdapterViewTextColor);

            if (!dayTime.isCurrentMonth()) {
                holder.mDayView.setTypeface(Typeface.DEFAULT_BOLD);
                holder.mDayView.setBackgroundColor(mDisabledBackgroundColor);
                holder.mDayView.setTextColor(mDisabledTextColor);
                holder.mDayView.setEnabled(false);
                holder.mDayView.setClickable(false);
            }

            if (dayTime.isWeekend() && dayTime.isCurrentMonth()) {
//                holder.mDayView.setBackgroundColor(mWeekendBackgroundColor);
                holder.mDayView.setTextColor(mWeekendTextColor);
                holder.mDayView.setTypeface(Typeface.DEFAULT_BOLD);
                holder.mDayView.setEnabled(true);
                holder.mDayView.setClickable(true);
            }

            if (dayTime.isCurrentDay() && dayTime.isCurrentMonth()) {
                holder.mDayView.setBackgroundColor(mCurrentBackgroundColor);
                holder.mDayView.setTextColor(mCurrentTextColor);
                holder.mDayView.setEnabled(true);
                holder.mDayView.setClickable(true);
            }

            holder.mDayView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnDateSelectedListener) {
                        mOnDateSelectedListener.onDateSelected(v, dayTime.getYear(), dayTime.getMonth(), dayTime.getDay(), dayTime.getEventList());
                    }
                }
            });

            holder.mDayView.invalidate();
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }
}