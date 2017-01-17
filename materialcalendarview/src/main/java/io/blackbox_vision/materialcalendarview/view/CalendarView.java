/*
 * Copyright (C) 2015 Jonatan Ezequiel Salas
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
package io.blackbox_vision.materialcalendarview.view;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.blackbox_vision.materialcalendarview.R;
import io.blackbox_vision.materialcalendarview.decor.DayDecorator;
import io.blackbox_vision.materialcalendarview.utils.CalendarUtils;

/***
 * Custom CalendarView class.
 *
 * @author jonatan.salas
 */
public class CalendarView extends LinearLayout {

    /**
     * Indicates that the CalendarView is in an idle, settled state. The current page
     * is fully in view and no animation is in progress.
     */
    int SCROLL_STATE_IDLE = 0;

    /**
     * Indicates that the CalendarView is currently being dragged by the user.
     */
    int SCROLL_STATE_DRAGGING = 1;

    /**
     * Indicates that the CalendarView is in the process of settling to a final position.
     */
    int SCROLL_STATE_SETTLING = 2;

    boolean USE_CACHE = false;
    int MIN_DISTANCE_FOR_FLING = 25; // dips
    int DEFAULT_GUTTER_SIZE = 16; // dips
    int MIN_FLING_VELOCITY = 400; // dips

    /**
     * Sentinel value for no current active pointer.
     */
    int INVALID_POINTER = -1;

    // If the CalendarView is at least this close to its final position, complete the scroll
    // on touch down and let the user interact with the content inside instead of
    // "catching" the flinging Calendar.
    int CLOSE_ENOUGH = 2; // dp

    private boolean mScrollingCacheEnabled;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private int mDefaultGutterSize;
    private int mTouchSlop;

    /**
     * Position of the last motion event.
     */
    private float mLastMotionX;
    private float mLastMotionY;
    private float mInitialMotionX;
    private float mInitialMotionY;

    private Scroller mScroller;

    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     */
    private int mActivePointerId = INVALID_POINTER;

    /**
     * Determines speed during touch scrolling
     */
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mFlingDistance;
    private int mCloseEnough;

    private int mScrollState = SCROLL_STATE_IDLE;

    private final Runnable mEndScrollRunnable = new Runnable() {
        public void run() {
            setScrollState(SCROLL_STATE_IDLE);
        }
    };

    // Gesture Detector used to handle Swipe gestures.
    private GestureDetectorCompat mGestureDetector;
    private Context mContext;
    private View mView;
    private ImageView mNextButton;
    private ImageView mBackButton;

    //Listeners used by the Calendar...
    private OnMonthTitleClickListener mOnMonthTitleClickListener;
    private OnDateClickListener mOnDateClickListener;
    private OnDateLongClickListener mOnDateLongClickListener;
    private OnMonthChangedListener mOnMonthChangedListener;

    private Calendar mCalendar;
    private Date mLastSelectedDay;

    //Customizable variables...
    private Typeface mTypeface;
    private int mDisabledDayBackgroundColor;
    private int mDisabledDayTextColor;
    private int mCalendarBackgroundColor;
    private int mSelectedDayBackground;
    private int mWeekLayoutBackgroundColor;
    private int mCalendarTitleBackgroundColor;
    private int mSelectedDayTextColor;
    private int mCalendarTitleTextColor;
    private int mDayOfWeekTextColor;
    private int mCurrentDayOfMonth;
    private int mWeekendColor;
    private int mWeekend;

    private List<DayDecorator> mDecoratorsList = null;
    private boolean mIsOverflowDateVisible = true;
    private int mFirstDayOfWeek = Calendar.SUNDAY;
    private int mCurrentMonthIndex = 0;

    // Day of weekend
    private int[] mTotalDayOfWeekend;

    // true for ordinary day, false for a weekend.
    private boolean mIsCommonDay;

    /**
     * Constructor with arguments. It receives a
     * Context used to get the resources.
     *
     * @param context - the context used to get the resources.
     */
    public CalendarView(Context context) {
        this(context, null);

        //Initialize the gesture listener needed..
        mGestureDetector = new GestureDetectorCompat(context, new CalendarGestureDetector());
    }

    /**
     * Constructor with arguments. It receives a
     * Context used to get the resources.
     *
     * @param context - the context used to get the resources.
     * @param attrs - attribute set with custom styles.
     */
    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        //Initialize the gesture listener needed..
        mGestureDetector = new GestureDetectorCompat(context, new CalendarGestureDetector());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode()) {
                return;
            }
        }

        getAttributes(attrs);
        init();
    }

    /***
     * Method that gets and set the attributes of the CalendarView class.
     *
     * @param attrs - Attribute set object with custom values to be setted
     */
    private void getAttributes(AttributeSet attrs) {
        final TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MaterialCalendarView, 0, 0);

        final int white = ContextCompat.getColor(mContext, android.R.color.white);
        final int black = ContextCompat.getColor(mContext, android.R.color.black);
        final int dayDisableBackground = ContextCompat.getColor(mContext, R.color.day_disabled_background_color);
        final int dayDisableTextColor = ContextCompat.getColor(mContext, R.color.day_disabled_text_color);
        final int daySelectedBackground = ContextCompat.getColor(mContext, R.color.selected_day_background);
        final int dayCurrent = ContextCompat.getColor(mContext, R.color.current_day_of_month);
        final int weekendColor = ContextCompat.getColor(mContext, R.color.weekend_color);

        try {
            mCalendarBackgroundColor = a.getColor(R.styleable.MaterialCalendarView_calendarBackgroundColor, white);
            mCalendarTitleBackgroundColor = a.getColor(R.styleable.MaterialCalendarView_titleLayoutBackgroundColor, white);
            mCalendarTitleTextColor = a.getColor(R.styleable.MaterialCalendarView_calendarTitleTextColor, black);
            mWeekLayoutBackgroundColor = a.getColor(R.styleable.MaterialCalendarView_weekLayoutBackgroundColor, white);
            mDayOfWeekTextColor = a.getColor(R.styleable.MaterialCalendarView_dayOfWeekTextColor, black);
            mDisabledDayBackgroundColor = a.getColor(R.styleable.MaterialCalendarView_disabledDayBackgroundColor, dayDisableBackground);
            mDisabledDayTextColor = a.getColor(R.styleable.MaterialCalendarView_disabledDayTextColor, dayDisableTextColor);
            mSelectedDayBackground = a.getColor(R.styleable.MaterialCalendarView_selectedDayBackgroundColor, daySelectedBackground);
            mSelectedDayTextColor = a.getColor(R.styleable.MaterialCalendarView_selectedDayTextColor, white);
            mCurrentDayOfMonth = a.getColor(R.styleable.MaterialCalendarView_currentDayOfMonthColor, dayCurrent);
            mWeekendColor = a.getColor(R.styleable.MaterialCalendarView_weekendColor, weekendColor);
            mWeekend = a.getInteger(R.styleable.MaterialCalendarView_weekend, 0);
        } finally {
            if (null != a) {
                a.recycle();
            }
        }
    }

    /**
     * This method init all necessary variables and Views that our Calendar is going to use.
     */
    private void init() {
        mScroller = new Scroller(mContext, null);

        //Variables associated to handle touch events..
        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        final float density = mContext.getResources().getDisplayMetrics().density;

        //Variables associated to Swipe..
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mMinimumVelocity = (int) (MIN_FLING_VELOCITY * density);
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mFlingDistance = (int) (MIN_DISTANCE_FOR_FLING * density);
        mCloseEnough = (int) (CLOSE_ENOUGH * density);
        mDefaultGutterSize = (int) (DEFAULT_GUTTER_SIZE * density);

        //Inflate current view..
        mView = LayoutInflater.from(mContext).inflate(R.layout.material_calendar_with_title, this, true);

        //Get buttons for Calendar and set it´s listeners..
        mBackButton = (ImageView) mView.findViewById(R.id.left_button);
        mNextButton = (ImageView) mView.findViewById(R.id.right_button);

        mBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentMonthIndex--;
                mCalendar = Calendar.getInstance(Locale.getDefault());
                mCalendar.add(Calendar.MONTH, mCurrentMonthIndex);

                refreshCalendar(mCalendar);
                if (mOnMonthChangedListener != null) {
                    mOnMonthChangedListener.onMonthChanged(mCalendar.getTime());
                }
            }
        });

        mNextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentMonthIndex++;
                mCalendar = Calendar.getInstance(Locale.getDefault());
                mCalendar.add(Calendar.MONTH, mCurrentMonthIndex);
                refreshCalendar(mCalendar);

                if (mOnMonthChangedListener != null) {
                    mOnMonthChangedListener.onMonthChanged(mCalendar.getTime());
                }
            }
        });

        setFirstDayOfWeek(Calendar.SUNDAY);
        refreshCalendar(Calendar.getInstance(getLocale()));
    }

    /**
     * Display calendar title with next previous month button
     */
    private void initTitleLayout() {
        View titleLayout = mView.findViewById(R.id.title_layout);
        titleLayout.setBackgroundColor(mCalendarTitleBackgroundColor);

        TextView dateTitle = (TextView) mView.findViewById(R.id.dateTitle);

        String dateText = CalendarUtils.getCurrentMonth(mCurrentMonthIndex).toUpperCase(Locale.getDefault()) + " " + getCurrentYear();
        dateTitle.setText(dateText);
        dateTitle.setTextColor(mCalendarTitleTextColor);

        if (null != getTypeface()) {
            dateTitle.setTypeface(getTypeface(), Typeface.BOLD);
        }

        dateTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mOnMonthTitleClickListener != null) {
                    mOnMonthTitleClickListener.onMonthTitleClick(mCalendar.getTime());
                    createDialogWithoutDateField(mContext);
                }
            }
        });
    }

    /**
     * Initialize the calendar week layout, considers start day
     */
    private void initWeekLayout() {
        TextView dayOfWeek;
        String dayOfTheWeekString;

        //Setting background color white
        View titleLayout = mView.findViewById(R.id.week_layout);
        titleLayout.setBackgroundColor(mWeekLayoutBackgroundColor);

        final String[] weekDaysArray = new DateFormatSymbols(getLocale()).getShortWeekdays();
        for (int i = 1; i < weekDaysArray.length; i++) {
            dayOfTheWeekString = weekDaysArray[i];
            int length = dayOfTheWeekString.length() < 3 ? dayOfTheWeekString.length() : 3;
            dayOfTheWeekString = dayOfTheWeekString.substring(0, length).toUpperCase();
            dayOfWeek = (TextView) mView.findViewWithTag(mContext.getString(R.string.day_of_week) + CalendarUtils.getWeekIndex(i, mCalendar));
            dayOfWeek.setText(dayOfTheWeekString);
            mIsCommonDay = true;
            if(totalDayOfWeekend().length != 0) {
                for (int weekend : totalDayOfWeekend()) {
                    if (i == weekend) {
                        dayOfWeek.setTextColor(mWeekendColor);
                        mIsCommonDay = false;
                    }
                }
            }

            if(mIsCommonDay) {
                dayOfWeek.setTextColor(mDayOfWeekTextColor);
            }

            if (null != getTypeface()) {
                dayOfWeek.setTypeface(getTypeface());
            }
        }
    }

    /**
     * Date Picker (Month & Year only)
     *
     * @param context
     * @author chris.chen
     */
    private void createDialogWithoutDateField(Context context) {

        mCalendar = Calendar.getInstance(Locale.getDefault());
        final int iYear = mCalendar.get(Calendar.YEAR);
        final int iMonth = mCalendar.get(Calendar.MONTH);
        final int iDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(context, R.style.CalendarViewTitle, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(
                    DatePicker datePicker,
                    int year,
                    int monthOfYear,
                    int dayOfMonth
            ) {

                int diffMonth = (year - iYear) * 12 + (monthOfYear - iMonth);

                mCurrentMonthIndex = diffMonth;
                mCalendar.add(Calendar.MONTH, mCurrentMonthIndex);

                refreshCalendar(mCalendar);
                if (mOnMonthChangedListener != null) {
                    mOnMonthChangedListener.onMonthChanged(mCalendar.getTime());
                }

            }
        }, iYear, iMonth, iDay);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
            if (daySpinnerId != 0) {
                View daySpinner = dpd.getDatePicker().findViewById(daySpinnerId);
                if (daySpinner != null) {
                    daySpinner.setVisibility(View.GONE);
                }
            }

            int monthSpinnerId = Resources.getSystem().getIdentifier("month", "id", "android");
            if (monthSpinnerId != 0) {
                View monthSpinner = dpd.getDatePicker().findViewById(monthSpinnerId);
                if (monthSpinner != null) {
                    monthSpinner.setVisibility(View.VISIBLE);
                }
            }

            int yearSpinnerId = Resources.getSystem().getIdentifier("year", "id", "android");
            if (yearSpinnerId != 0) {
                View yearSpinner = dpd.getDatePicker().findViewById(yearSpinnerId);
                if (yearSpinner != null) {
                    yearSpinner.setVisibility(View.VISIBLE);
                }
            }

        } else { //Older SDK versions
            Field f[] = dpd.getDatePicker().getClass().getDeclaredFields();

            for (Field field : f) {
                if (field.getName().equals("mDayPicker") || field.getName().equals("mDaySpinner")) {
                    field.setAccessible(true);
                    Object dayPicker = null;

                    try {
                        dayPicker = field.get(dpd.getDatePicker());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    ((View) dayPicker).setVisibility(View.GONE);
                }

                if (field.getName().equals("mMonthPicker") || field.getName().equals("mMonthSpinner")) {
                    field.setAccessible(true);
                    Object monthPicker = null;

                    try {
                        monthPicker = field.get(dpd.getDatePicker());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    ((View) monthPicker).setVisibility(View.VISIBLE);
                }

                if (field.getName().equals("mYearPicker") || field.getName().equals("mYearSpinner")) {
                    field.setAccessible(true);
                    Object yearPicker = null;

                    try {
                        yearPicker = field.get(dpd.getDatePicker());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    ((View) yearPicker).setVisibility(View.VISIBLE);
                }
            }
        }

        dpd.show();
    }

    /**
     * This method prepare and populate the days in the CalendarView
     */
    private void setDaysInCalendar() {
        Calendar calendar = Calendar.getInstance(getLocale());
        calendar.setTime(mCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.setFirstDayOfWeek(mFirstDayOfWeek);
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        // Calculate dayOfMonthIndex
        int dayOfMonthIndex = CalendarUtils.getWeekIndex(firstDayOfMonth, calendar);
        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        final Calendar startCalendar = (Calendar) calendar.clone();
        //Add required number of days
        startCalendar.add(Calendar.DATE, -(dayOfMonthIndex - 1));
        int monthEndIndex = 42 - (actualMaximum + dayOfMonthIndex - 1);

        DayView dayView;
        ViewGroup dayOfMonthContainer;
        for (int i = 1; i < 43; i++) {
            dayOfMonthContainer = (ViewGroup) mView.findViewWithTag(mContext.getString(R.string.day_of_month_container) + i);
            dayView = (DayView) mView.findViewWithTag(mContext.getString(R.string.day_of_month_text) + i);
            if (dayView == null)
                continue;

            //Apply the default styles
            dayOfMonthContainer.setOnClickListener(null);
            dayView.bind(startCalendar.getTime(), getDecoratorsList());
            dayView.setVisibility(View.VISIBLE);

            if (null != getTypeface()) {
                dayView.setTypeface(getTypeface());
            }

            if (CalendarUtils.isSameMonth(calendar, startCalendar)) {
                dayOfMonthContainer.setOnClickListener(onDayOfMonthClickListener);
                dayOfMonthContainer.setOnLongClickListener(onDayOfMonthLongClickListener);
                dayView.setBackgroundColor(mCalendarBackgroundColor);
                mIsCommonDay = true;
                if(totalDayOfWeekend().length != 0) {
                    for (int weekend : totalDayOfWeekend()) {
                        if (startCalendar.get(Calendar.DAY_OF_WEEK) == weekend) {
                            dayView.setTextColor(mWeekendColor);
                            mIsCommonDay = false;
                        }
                    }
                }

                if(mIsCommonDay) {
                    dayView.setTextColor(mDayOfWeekTextColor);
                }
            } else {
                dayView.setBackgroundColor(mDisabledDayBackgroundColor);
                dayView.setTextColor(mDisabledDayTextColor);

                if (!isOverflowDateVisible())
                    dayView.setVisibility(View.GONE);
                else if (i >= 36 && ((float) monthEndIndex / 7.0f) >= 1) {
                    dayView.setVisibility(View.GONE);
                }
            }
            dayView.decorate();

            //Set the current day color
            if(mCalendar.get(Calendar.MONTH) == startCalendar.get(Calendar.MONTH) )
                setCurrentDay(mCalendar.getTime());

            startCalendar.add(Calendar.DATE, 1);
            dayOfMonthIndex++;
        }

        // If the last week row has no visible days, hide it or show it in case
        ViewGroup weekRow = (ViewGroup) mView.findViewWithTag("weekRow6");
        dayView = (DayView) mView.findViewWithTag("dayOfMonthText36");
        if (dayView.getVisibility() != VISIBLE) {
            weekRow.setVisibility(GONE);
        } else {
            weekRow.setVisibility(VISIBLE);
        }
    }

    private void clearDayOfTheMonthStyle(Date currentDate) {
        if (currentDate != null) {
            final Calendar calendar = CalendarUtils.getTodayCalendar(mContext, mFirstDayOfWeek);
            calendar.setFirstDayOfWeek(mFirstDayOfWeek);
            calendar.setTime(currentDate);

            final DayView dayView = findViewByCalendar(calendar);
            dayView.setBackgroundColor(mCalendarBackgroundColor);
            mIsCommonDay = true;
            if(totalDayOfWeekend().length != 0) {
                for (int weekend : totalDayOfWeekend()) {
                    if (calendar.get(Calendar.DAY_OF_WEEK) == weekend) {
                        dayView.setTextColor(mWeekendColor);
                        mIsCommonDay = false;
                    }
                }
            }

            if(mIsCommonDay) {
                dayView.setTextColor(mDayOfWeekTextColor);
            }
        }
    }

    public DayView findViewByDate(@NonNull Date dateToFind) {
        final Calendar calendar = Calendar.getInstance(getLocale());
        calendar.setTime(dateToFind);
        return (DayView) getView(mContext.getString(R.string.day_of_month_text), calendar);
    }

    private DayView findViewByCalendar(@NonNull Calendar calendarToFind) {
        return (DayView) getView(mContext.getString(R.string.day_of_month_text), calendarToFind);
    }

    private int getDayIndexByDate(Calendar calendar) {
        int monthOffset = CalendarUtils.getMonthOffset(calendar, mFirstDayOfWeek);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        return currentDay + monthOffset;
    }

    private View getView(String key, Calendar currentCalendar) {
        final int index = getDayIndexByDate(currentCalendar);
        return mView.findViewWithTag(key + index);
    }

    public void refreshCalendar(Calendar calendar) {
        mCalendar = calendar;
        mCalendar.setFirstDayOfWeek(mFirstDayOfWeek);

        initTitleLayout();
        setTotalDayOfWeekend();
        initWeekLayout();

        setDaysInCalendar();
    }

    private void setTotalDayOfWeekend() {
        int[] weekendDay = new int[Integer.bitCount(mWeekend)];
        char days[]= Integer.toBinaryString(mWeekend).toCharArray();
        int day = 1;
        int index = 0;
        for(int i = days.length - 1; i >= 0; i--) {
            if(days[i] == '1') {
                weekendDay[index] = day;
                index++;
            }
            day++;
        }

        mTotalDayOfWeekend = weekendDay;
    }

    private int[] totalDayOfWeekend() {
        return mTotalDayOfWeekend;
    }

    public void setCurrentDay(@NonNull Date todayDate) {
        final Calendar calendar = Calendar.getInstance(getLocale());
        calendar.setTime(todayDate);

        if (CalendarUtils.isToday(calendar)) {
            final DayView dayOfMonth = findViewByCalendar(calendar);

            dayOfMonth.setTextColor(mCurrentDayOfMonth);
            dayOfMonth.setBackgroundColor(mSelectedDayBackground);
        }
    }

    public void setDateAsSelected(Date currentDate) {
        final Calendar currentCalendar = CalendarUtils.getTodayCalendar(mContext, mFirstDayOfWeek);
        currentCalendar.setFirstDayOfWeek(mFirstDayOfWeek);
        currentCalendar.setTime(currentDate);

        // Clear previous marks
        clearDayOfTheMonthStyle(mLastSelectedDay);

        // Store current values as last values
        setLastSelectedDay(currentDate);

        // Mark current day as selected
        DayView view = findViewByCalendar(currentCalendar);
        view.setBackgroundColor(mSelectedDayBackground);
        view.setTextColor(mSelectedDayTextColor);
    }

    private OnLongClickListener onDayOfMonthLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            // Extract day selected
            ViewGroup dayOfMonthContainer = (ViewGroup) view;
            String tagId = (String) dayOfMonthContainer.getTag();
            tagId = tagId.substring(mContext.getString(R.string.day_of_month_container).length(), tagId.length());
            final TextView dayOfMonthText = (TextView) view.findViewWithTag(mContext.getString(R.string.day_of_month_text) + tagId);

            // Fire event
            final Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(mFirstDayOfWeek);
            calendar.setTime(mCalendar.getTime());
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dayOfMonthText.getText().toString()));
            setDateAsSelected(calendar.getTime());

            //Set the current day color
            setCurrentDay(mCalendar.getTime());

            if (mOnDateLongClickListener != null) {
                mOnDateLongClickListener.onDateLongClick(calendar.getTime());
            }

            return false;
        }
    };

    private OnClickListener onDayOfMonthClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // Extract day selected
            ViewGroup dayOfMonthContainer = (ViewGroup) view;
            String tagId = (String) dayOfMonthContainer.getTag();
            tagId = tagId.substring(mContext.getString(R.string.day_of_month_container).length(), tagId.length());
            final TextView dayOfMonthText = (TextView) view.findViewWithTag(mContext.getString(R.string.day_of_month_text) + tagId);

            // Fire event
            final Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(mFirstDayOfWeek);
            calendar.setTime(mCalendar.getTime());
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dayOfMonthText.getText().toString()));
            setDateAsSelected(calendar.getTime());

            //Set the current day color
            setCurrentDay(mCalendar.getTime());

            if (mOnDateClickListener != null) {
                mOnDateClickListener.onDateClick(calendar.getTime());
            }
        }
    };

    private boolean isGutterDrag(float x, float dx) {
        return (x < mDefaultGutterSize && dx > 0) || (x > getWidth() - mDefaultGutterSize && dx < 0);
    }

    private void setScrollingCacheEnabled(boolean enabled) {
        if (mScrollingCacheEnabled != enabled) {
            mScrollingCacheEnabled = enabled;
            if (USE_CACHE) {
                final int size = getChildCount();
                for (int i = 0; i < size; ++i) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() != GONE) {
                        child.setDrawingCacheEnabled(enabled);
                    }
                }
            }
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionX = MotionEventCompat.getX(ev, newPointerIndex);
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
    }

    private void setScrollState(int newState) {
        if (mScrollState == newState) {
            return;
        }

        mScrollState = newState;
    }

    private void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        final ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    /**
     * Tests scroll ability within child views of v given a delta of dx.
     *
     * @param v View to test for horizontal scroll ability
     * @param checkV Whether the view v passed should itself be checked for scrollability (true),
     *               or just its children (false).
     * @param dx Delta scrolled in pixels
     * @param x X coordinate of the active touch point
     * @param y Y coordinate of the active touch point
     * @return true if child views of v can be scrolled by delta of dx.
     */
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            final int count = group.getChildCount();
            // Count backwards - let topmost views consume scroll distance first.
            for (int i = count - 1; i >= 0; i--) {
                // This will not work for transformed views in Honeycomb+
                final View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() &&
                        y + scrollY >= child.getTop() && y + scrollY < child.getBottom() &&
                        canScroll(child, true, dx, x + scrollX - child.getLeft(),
                                y + scrollY - child.getTop())) {
                    return true;
                }
            }
        }

        return checkV && ViewCompat.canScrollHorizontally(v, -dx);
    }

    private void completeScroll(boolean postEvents) {
        boolean needPopulate = mScrollState == SCROLL_STATE_SETTLING;
        if (needPopulate) {
            // Done with scroll, no longer want to cache view drawing.
            setScrollingCacheEnabled(false);
            mScroller.abortAnimation();
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            if (oldX != x || oldY != y) {
                scrollTo(x, y);
            }
        }

        if (needPopulate) {
            if (postEvents) {
                ViewCompat.postOnAnimation(this, mEndScrollRunnable);
            } else {
                mEndScrollRunnable.run();
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(null != mGestureDetector) {
            mGestureDetector.onTouchEvent(ev);
            super.dispatchTouchEvent(ev);
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*
         * This method JUST determines whether we want to intercept the motion.
         * If we return true, onMotionEvent will be called and we do the actual
         * scrolling there.
         */

        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;

        // Always take care of the touch gesture being complete.
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Release the drag.
            mIsBeingDragged = false;
            mIsUnableToDrag = false;
            mActivePointerId = INVALID_POINTER;
            if (mVelocityTracker != null) {
                mVelocityTracker.recycle();
                mVelocityTracker = null;
            }
            return false;
        }

        // Nothing more to do here if we have decided whether or not we
        // are dragging.
        if (action != MotionEvent.ACTION_DOWN) {
            if (mIsBeingDragged) {
                return true;
            }
            if (mIsUnableToDrag) {
                return false;
            }
        }

        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                /*
                 * mIsBeingDragged == false, otherwise the shortcut would have caught it. Check
                 * whether the user has moved far enough from his original down touch.
                 */

                /*
                * Locally do absolute value. mLastMotionY is set to the y value
                * of the down event.
                */
                final int activePointerId = mActivePointerId;
                if (activePointerId == INVALID_POINTER) {
                    // If we don't have a valid id, the touch down wasn't on content.
                    break;
                }

                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerId);
                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float dx = x - mLastMotionX;
                final float xDiff = Math.abs(dx);
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float yDiff = Math.abs(y - mInitialMotionY);

                if (dx != 0 && !isGutterDrag(mLastMotionX, dx) &&
                        canScroll(this, false, (int) dx, (int) x, (int) y)) {
                    // Nested view has scrollable area under this point. Let it be handled there.
                    mLastMotionX = x;
                    mLastMotionY = y;
                    mIsUnableToDrag = true;
                    return false;
                }
                if (xDiff > mTouchSlop && xDiff * 0.5f > yDiff) {
                    mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(SCROLL_STATE_DRAGGING);
                    mLastMotionX = dx > 0 ? mInitialMotionX + mTouchSlop :
                            mInitialMotionX - mTouchSlop;
                    mLastMotionY = y;
                    setScrollingCacheEnabled(true);
                } else if (yDiff > mTouchSlop) {
                    // The finger has moved enough in the vertical
                    // direction to be counted as a drag...  abort
                    // any attempt to drag horizontally, to work correctly
                    // with children that have scrolling containers.
                    mIsUnableToDrag = true;
                }
                break;
            }

            case MotionEvent.ACTION_DOWN: {
                /*
                 * Remember location of down touch.
                 * ACTION_DOWN always refers to pointer index 0.
                 */
                mLastMotionX = mInitialMotionX = ev.getX();
                mLastMotionY = mInitialMotionY = ev.getY();
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsUnableToDrag = false;

                mScroller.computeScrollOffset();
                if (mScrollState == SCROLL_STATE_SETTLING &&
                        Math.abs(mScroller.getFinalX() - mScroller.getCurrX()) > mCloseEnough) {
                    mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(SCROLL_STATE_DRAGGING);
                } else {
                    completeScroll(false);
                    mIsBeingDragged = false;
                }
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        mVelocityTracker.addMovement(ev);

        /*
         * The only time we want to intercept motion events is if we are in the
         * drag mode.
         */
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * CalendarGestureDetector class used to detect Swipes gestures.
     *
     * @author jonatan.salas
     */
    public class CalendarGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > mTouchSlop && Math.abs(velocityX) > mMinimumVelocity && Math.abs(velocityX) < mMaximumVelocity) {
                        if (e2.getX() - e1.getX() > mFlingDistance) {
                            mCurrentMonthIndex--;
                            mCalendar = Calendar.getInstance(Locale.getDefault());
                            mCalendar.add(Calendar.MONTH, mCurrentMonthIndex);
                            refreshCalendar(mCalendar);

                            if (mOnMonthChangedListener != null) {
                                mOnMonthChangedListener.onMonthChanged(mCalendar.getTime());
                            }

                        } else if(e1.getX() - e2.getX() > mFlingDistance) {
                            mCurrentMonthIndex++;
                            mCalendar = Calendar.getInstance(Locale.getDefault());
                            mCalendar.add(Calendar.MONTH, mCurrentMonthIndex);

                            refreshCalendar(mCalendar);
                            if (mOnMonthChangedListener != null) {
                                mOnMonthChangedListener.onMonthChanged(mCalendar.getTime());
                            }
                        }
                    }
                }

                return true;

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    /**
     * Interface that define a method to
     * implement to handle a selected date event,
     *
     * @author jonatan.salas
     */
    public interface OnDateClickListener {

        /**
         * Method that lets you handle
         * when a user touches a particular date.
         *
         * @param selectedDate - the date selected by the user.
         */
        void onDateClick(@NonNull Date selectedDate);
    }

    /**
     * Interface that define a method to
     * implement to handle a selected date event,
     *
     * @author jonatan.salas
     */
    public interface OnDateLongClickListener {

        /**
         * Method that lets you handle
         * when a user touches a particular date.
         *
         * @param selectedDate - the date selected by the user.
         */
        void onDateLongClick(@NonNull Date selectedDate);
    }


    /**
     * Interface that define a method to implement to handle
     * a month changed event.
     *
     * @author jonatan.salas
     */
    public interface OnMonthChangedListener {

        /**
         * Method that lets you handle when a goes to back or next
         * month.
         *
         * @param monthDate - the date with the current month
         */
        void onMonthChanged(@NonNull Date monthDate);
    }

    /**
     * Interface that define a method to implement to handle
     * a month title change event.
     *
     * @author chris.chen
     */
    public interface OnMonthTitleClickListener {
        void onMonthTitleClick(@NonNull Date monthDate);
    }

    /**
     *  Attributes setters and getters.
     */

    public void setOnMonthTitleClickListener(OnMonthTitleClickListener onMonthTitleClickListener) {
        this.mOnMonthTitleClickListener = onMonthTitleClickListener;
    }

    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        this.mOnDateClickListener = onDateClickListener;
    }

    public void setOnDateLongClickListener(OnDateLongClickListener onDateLongClickListener) {
        this.mOnDateLongClickListener = onDateLongClickListener;
    }

    public void setOnMonthChangedListener(OnMonthChangedListener onMonthChangedListener) {
        this.mOnMonthChangedListener = onMonthChangedListener;
    }

    private void setLastSelectedDay(Date lastSelectedDay) {
        this.mLastSelectedDay = lastSelectedDay;
    }

    public void setTypeface(Typeface typeface) {
        this.mTypeface = typeface;
    }

    public void setDecoratorsList(List<DayDecorator> decoratorsList) {
        this.mDecoratorsList = decoratorsList;
    }

    public void setIsOverflowDateVisible(boolean isOverflowDateVisible) {
        this.mIsOverflowDateVisible = isOverflowDateVisible;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.mFirstDayOfWeek = firstDayOfWeek;
    }

    public void setDisabledDayBackgroundColor(int disabledDayBackgroundColor) {
        this.mDisabledDayBackgroundColor = disabledDayBackgroundColor;
    }

    public void setDisabledDayTextColor(int disabledDayTextColor) {
        this.mDisabledDayTextColor = disabledDayTextColor;
    }

    public void setCalendarBackgroundColor(int calendarBackgroundColor) {
        this.mCalendarBackgroundColor = calendarBackgroundColor;
    }

    public void setSelectedDayBackground(int selectedDayBackground) {
        this.mSelectedDayBackground = selectedDayBackground;
    }

    public void setWeekLayoutBackgroundColor(int weekLayoutBackgroundColor) {
        this.mWeekLayoutBackgroundColor = weekLayoutBackgroundColor;
    }

    public void setCalendarTitleBackgroundColor(int calendarTitleBackgroundColor) {
        this.mCalendarTitleBackgroundColor = calendarTitleBackgroundColor;
    }

    public void setSelectedDayTextColor(int selectedDayTextColor) {
        this.mSelectedDayTextColor = selectedDayTextColor;
    }

    public void setCalendarTitleTextColor(int calendarTitleTextColor) {
        this.mCalendarTitleTextColor = calendarTitleTextColor;
    }

    public void setDayOfWeekTextColor(int dayOfWeekTextColor) {
        this.mDayOfWeekTextColor = dayOfWeekTextColor;
    }

    public void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.mCurrentDayOfMonth = currentDayOfMonth;
    }

    public void setWeekendColor(int weekendColor) {
        this.mWeekendColor = weekendColor;
    }

    public void setWeekend(int weekend) {
        this.mWeekend = weekend;
    }

    public void setBackButtonColor(@ColorRes int colorId) {
        this.mBackButton.setColorFilter(ContextCompat.getColor(mContext, colorId), PorterDuff.Mode.SRC_ATOP);
    }

    public void setNextButtonColor(@ColorRes int colorId) {
        this.mNextButton.setColorFilter(ContextCompat.getColor(mContext, colorId), PorterDuff.Mode.SRC_ATOP);
    }

    public void setBackButtonDrawable(@DrawableRes int drawableId) {
        this.mBackButton.setImageDrawable(ContextCompat.getDrawable(mContext, drawableId));
    }

    public void setNextButtonDrawable(@DrawableRes int drawableId) {
        this.mNextButton.setImageDrawable(ContextCompat.getDrawable(mContext, drawableId));
    }

    public Typeface getTypeface() {
        return mTypeface;
    }

    public List<DayDecorator> getDecoratorsList() {
        return mDecoratorsList;
    }

    public Locale getLocale() {
        return mContext.getResources().getConfiguration().locale;
    }

    public String getCurrentMonth() {
        return CalendarUtils.getCurrentMonth(mCurrentMonthIndex);
    }

    public String getCurrentYear() {
        return String.valueOf(mCalendar.get(Calendar.YEAR));
    }

    public boolean isOverflowDateVisible() {
        return mIsOverflowDateVisible;
    }
}
