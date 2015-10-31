package com.samsistemas.calendarview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samsistemas.calendarview.R;
import com.samsistemas.calendarview.decor.DayDecorator;
import com.samsistemas.calendarview.util.AttributeUtil;
import com.samsistemas.calendarview.util.CalendarUtil;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/***
 * @author jonatan.salas
 */
public class CalendarView extends LinearLayout {
    private static final int SWIPE_THRESHOLD = 150;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private GestureDetectorCompat mGestureDetector;
    private Context mContext;
    private View mView;

    private OnDateSelectedListener mOnDateSelectedListener;
    private OnMonthChangedListener mOnMonthChangedListener;

    private Calendar mCalendar;
    private Locale mLocale;
    private Date mLastSelectedDay;

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

    private List<DayDecorator> mDecoratorsList = null;
    private boolean mIsOverflowDateVisible = true;
    private int mFirstDayOfWeek = Calendar.SUNDAY;
    private int mCurrentMonthIndex = 0;

    /**
     * @param context
     */
    public CalendarView(Context context) {
        this(context, null);
        mGestureDetector = new GestureDetectorCompat(context, new CalendarGestureDetector());
    }

    /**
     * @param context
     * @param attrs
     */
    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mGestureDetector = new GestureDetectorCompat(context, new CalendarGestureDetector());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode())
                return;
        }

        getAttributes(attrs);
        init();
    }

    private void getAttributes(AttributeSet attrs) {
        final int[] stylesArray = AttributeUtil.getAttributes(mContext, attrs);

        mCalendarBackgroundColor = stylesArray[0];
        mCalendarTitleBackgroundColor = stylesArray[1];
        mCalendarTitleTextColor = stylesArray[2];
        mWeekLayoutBackgroundColor = stylesArray[3];
        mDayOfWeekTextColor = stylesArray[4];
        mDisabledDayBackgroundColor = stylesArray[5];
        mDisabledDayTextColor = stylesArray[6];
        mSelectedDayBackground = stylesArray[7];
        mSelectedDayTextColor = stylesArray[8];
        mCurrentDayOfMonth = stylesArray[9];
    }

    private void init() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.custom_calendar_layout, this, true);

        final ImageView backButton = (ImageView) mView.findViewById(R.id.left_button);
        final ImageView nextButton = (ImageView) mView.findViewById(R.id.right_button);

        backButton.setOnClickListener(new OnClickListener() {
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

        nextButton.setOnClickListener(new OnClickListener() {
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

        dateTitle.setText(getCurrentMonth());
        dateTitle.setTextColor(mCalendarTitleTextColor);

        if (null != getTypeface()) {
            dateTitle.setTypeface(getTypeface(), Typeface.BOLD);
        }
    }

    /**
     * Initialize the calendar week layout, considers start day
     */
    @SuppressLint("DefaultLocale")
    private void initWeekLayout() {
        TextView dayOfWeek;
        String dayOfTheWeekString;

        //Setting background color white
        View titleLayout = mView.findViewById(R.id.week_layout);
        titleLayout.setBackgroundColor(mWeekLayoutBackgroundColor);

        final String[] weekDaysArray = new DateFormatSymbols(mLocale).getShortWeekdays();
        for (int i = 1; i < weekDaysArray.length; i++) {
            dayOfTheWeekString = weekDaysArray[i];
            dayOfTheWeekString = dayOfTheWeekString.substring(0, 3).toUpperCase();
            dayOfWeek = (TextView) mView.findViewWithTag(mContext.getString(R.string.day_of_week) + CalendarUtil.getWeekIndex(i, mCalendar));
            dayOfWeek.setText(dayOfTheWeekString);
            dayOfWeek.setTextColor(mDayOfWeekTextColor);

            if (null != getTypeface()) {
                dayOfWeek.setTypeface(getTypeface());
            }
        }
    }

    private void setDaysInCalendar() {
        Calendar calendar = Calendar.getInstance(mLocale);
        calendar.setTime(mCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.setFirstDayOfWeek(mFirstDayOfWeek);
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        // Calculate dayOfMonthIndex
        int dayOfMonthIndex = CalendarUtil.getWeekIndex(firstDayOfMonth, calendar);
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

            if (CalendarUtil.isSameMonth(calendar, startCalendar)) {
                dayOfMonthContainer.setOnClickListener(onDayOfMonthClickListener);
                dayView.setBackgroundColor(mCalendarBackgroundColor);
                dayView.setTextColor(mDayOfWeekTextColor);
            } else {
                dayView.setBackgroundColor(mDisabledDayBackgroundColor);
                dayView.setTextColor(mDisabledDayTextColor);

                if (!isIsOverflowDateVisible())
                    dayView.setVisibility(View.GONE);
                else if (i >= 36 && ((float) monthEndIndex / 7.0f) >= 1) {
                    dayView.setVisibility(View.GONE);
                }
            }
            dayView.decorate();

            //Set the current day color
            markDayAsCurrentDay(startCalendar);

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
            final Calendar calendar = CalendarUtil.getTodayCalendar(mContext, mFirstDayOfWeek);
            calendar.setFirstDayOfWeek(mFirstDayOfWeek);
            calendar.setTime(currentDate);

            final DayView dayView = getDayOfMonthText(calendar);
            dayView.setBackgroundColor(mCalendarBackgroundColor);
            dayView.setTextColor(mDayOfWeekTextColor);
        }
    }

    private DayView getDayOfMonthText(Calendar currentCalendar) {
        return (DayView) getView(mContext.getString(R.string.day_of_month_text), currentCalendar);
    }

    private int getDayIndexByDate(Calendar calendar) {
        int monthOffset = CalendarUtil.getMonthOffset(calendar, mFirstDayOfWeek);
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
        mLocale = getLocale();

        initTitleLayout();
        initWeekLayout();

        setDaysInCalendar();
    }

    public void markDayAsCurrentDay(Calendar calendar) {
        if (calendar != null && CalendarUtil.isToday(calendar)) {
            DayView dayOfMonth = getDayOfMonthText(calendar);
            dayOfMonth.setTextColor(mCurrentDayOfMonth);
        }
    }

    public void markDayAsSelectedDay(Date currentDate) {
        final Calendar currentCalendar = CalendarUtil.getTodayCalendar(mContext, mFirstDayOfWeek);
        currentCalendar.setFirstDayOfWeek(mFirstDayOfWeek);
        currentCalendar.setTime(currentDate);

        // Clear previous marks
        clearDayOfTheMonthStyle(mLastSelectedDay);

        // Store current values as last values
        setLastSelectedDay(currentDate);

        // Mark current day as selected
        DayView view = getDayOfMonthText(currentCalendar);
        view.setBackgroundColor(mSelectedDayBackground);
        view.setTextColor(mSelectedDayTextColor);
    }

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
            markDayAsSelectedDay(calendar.getTime());

            //Set the current day color
            markDayAsCurrentDay(mCalendar);

            if (mOnDateSelectedListener != null)
                mOnDateSelectedListener.onDateSelected(calendar.getTime());
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ViewConfiguration vc = ViewConfiguration.get(mView.getContext());
        final int action = MotionEventCompat.getActionMasked(ev);
        int slop = vc.getScaledTouchSlop();
        float downX = 0;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getRawX();
            case MotionEvent.ACTION_MOVE:
                float deltaX = ev.getRawX() - downX;
                if (Math.abs(deltaX) > slop) {
                    return true;
                } else if(Math.abs(deltaX) > slop) {
                    return true;
                }
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * @author jonatan.salas
     */
    public class CalendarGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            mCurrentMonthIndex++;
                            mCalendar = Calendar.getInstance(Locale.getDefault());
                            mCalendar.add(Calendar.MONTH, mCurrentMonthIndex);
                            refreshCalendar(mCalendar);

                            if (mOnMonthChangedListener != null) {
                                mOnMonthChangedListener.onMonthChanged(mCalendar.getTime());
                            }

                        } else {
                            mCurrentMonthIndex--;
                            mCalendar = Calendar.getInstance(Locale.getDefault());
                            mCalendar.add(Calendar.MONTH, mCurrentMonthIndex);

                            refreshCalendar(mCalendar);
                            if (mOnMonthChangedListener != null) {
                                mOnMonthChangedListener.onMonthChanged(mCalendar.getTime());
                            }
                        }
                    }
                    result = true;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return result;
        }
    }

    /**
     * @author jonatan.salas
     */
    public interface OnDateSelectedListener {

        /**
         *
         * @param selectedDate
         */
        void onDateSelected(@NonNull Date selectedDate);
    }

    /**
     * @author jonatan.salas
     */
    public interface OnMonthChangedListener {

        /**
         *
         * @param monthDate
         */
        void onMonthChanged(@NonNull Date monthDate);
    }


    /**
     *  Attributes setters and getters.
     */
    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.mOnDateSelectedListener = onDateSelectedListener;
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
        return new DateFormatSymbols(mLocale).getMonths()[mCalendar.get(Calendar.MONTH)].toUpperCase();
    }

    public boolean isIsOverflowDateVisible() {
        return mIsOverflowDateVisible;
    }
}
