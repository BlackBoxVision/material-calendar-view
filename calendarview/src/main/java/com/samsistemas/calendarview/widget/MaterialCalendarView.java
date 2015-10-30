package com.samsistemas.calendarview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samsistemas.calendarview.R;
import com.samsistemas.calendarview.decor.DayDecorator;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class MaterialCalendarView extends LinearLayout {
    private Context mContext;

    private View view;
    private ImageView previousMonthButton;
    private ImageView nextMonthButton;

    private CalendarListener calendarListener;
    private Calendar currentCalendar;
    private Locale locale;

    private Date lastSelectedDay;
    private Typeface customTypeface;

    private int firstDayOfWeek = Calendar.SUNDAY;

    private List<DayDecorator> decorators = null;

    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static final String DAY_OF_MONTH_TEXT = "dayOfMonthText";
    private static final String DAY_OF_MONTH_CONTAINER = "dayOfMonthContainer";

    private int disabledDayBackgroundColor;
    private int disabledDayTextColor;
    private int calendarBackgroundColor;
    private int selectedDayBackground;
    private int weekLayoutBackgroundColor;
    private int calendarTitleBackgroundColor;
    private int selectedDayTextColor;
    private int calendarTitleTextColor;
    private int dayOfWeekTextColor;
    private int dayOfMonthTextColor;
    private int currentDayOfMonth;

    private int currentMonthIndex = 0;
    private boolean isOverflowDateVisible = true;

    public MaterialCalendarView(Context mContext) {
        this(mContext, null);
    }

    public MaterialCalendarView(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode())
                return;
        }

        getAttributes(attrs);

        initializeCalendar();
    }

    private void getAttributes(AttributeSet attrs) {
        final TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CustomCalendarView, 0, 0);
        calendarBackgroundColor = typedArray.getColor(R.styleable.CustomCalendarView_calendarBackgroundColor, getResources().getColor(R.color.white));
        calendarTitleBackgroundColor = typedArray.getColor(R.styleable.CustomCalendarView_titleLayoutBackgroundColor, getResources().getColor(R.color.white));
        calendarTitleTextColor = typedArray.getColor(R.styleable.CustomCalendarView_calendarTitleTextColor, getResources().getColor(R.color.black));
        weekLayoutBackgroundColor = typedArray.getColor(R.styleable.CustomCalendarView_weekLayoutBackgroundColor, getResources().getColor(R.color.white));
        dayOfWeekTextColor = typedArray.getColor(R.styleable.CustomCalendarView_dayOfWeekTextColor, getResources().getColor(R.color.black));
        dayOfMonthTextColor = typedArray.getColor(R.styleable.CustomCalendarView_dayOfMonthTextColor, getResources().getColor(R.color.black));
        disabledDayBackgroundColor = typedArray.getColor(R.styleable.CustomCalendarView_disabledDayBackgroundColor, getResources().getColor(R.color.day_disabled_background_color));
        disabledDayTextColor = typedArray.getColor(R.styleable.CustomCalendarView_disabledDayTextColor, getResources().getColor(R.color.day_disabled_text_color));
        selectedDayBackground = typedArray.getColor(R.styleable.CustomCalendarView_selectedDayBackgroundColor, getResources().getColor(R.color.selected_day_background));
        selectedDayTextColor = typedArray.getColor(R.styleable.CustomCalendarView_selectedDayTextColor, getResources().getColor(R.color.white));
        currentDayOfMonth = typedArray.getColor(R.styleable.CustomCalendarView_currentDayOfMonthColor, getResources().getColor(R.color.current_day_of_month));
        typedArray.recycle();
    }

    private void initializeCalendar() {
        final LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflate.inflate(R.layout.custom_calendar_layout, this, true);
        previousMonthButton = (ImageView) view.findViewById(R.id.leftButton);
        nextMonthButton = (ImageView) view.findViewById(R.id.rightButton);

        previousMonthButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonthIndex--;
                currentCalendar = Calendar.getInstance(Locale.getDefault());
                currentCalendar.add(Calendar.MONTH, currentMonthIndex);

                refreshCalendar(currentCalendar);
                if (calendarListener != null) {
                    calendarListener.onMonthChanged(currentCalendar.getTime());
                }
            }
        });

        nextMonthButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonthIndex++;
                currentCalendar = Calendar.getInstance(Locale.getDefault());
                currentCalendar.add(Calendar.MONTH, currentMonthIndex);
                refreshCalendar(currentCalendar);

                if (calendarListener != null) {
                    calendarListener.onMonthChanged(currentCalendar.getTime());
                }
            }
        });

        // Initialize calendar for current month
        Locale locale = mContext.getResources().getConfiguration().locale;
        Calendar currentCalendar = Calendar.getInstance(locale);

        setFirstDayOfWeek(Calendar.SUNDAY);
        refreshCalendar(currentCalendar);
    }


    /**
     * Display calendar title with next previous month button
     */
    private void initializeTitleLayout() {
        View titleLayout = view.findViewById(R.id.titleLayout);
        titleLayout.setBackgroundColor(calendarTitleBackgroundColor);

        String dateText = new DateFormatSymbols(locale).getShortMonths()[currentCalendar.get(Calendar.MONTH)].toString();
        dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length());

        TextView dateTitle = (TextView) view.findViewById(R.id.dateTitle);
        dateTitle.setTextColor(calendarTitleTextColor);
        dateTitle.setText(dateText + " " + currentCalendar.get(Calendar.YEAR));
        dateTitle.setTextColor(calendarTitleTextColor);
        if (null != getCustomTypeface()) {
            dateTitle.setTypeface(getCustomTypeface(), Typeface.BOLD);
        }

    }

    /**
     * Initialize the calendar week layout, considers start day
     */
    @SuppressLint("DefaultLocale")
    private void initializeWeekLayout() {
        TextView dayOfWeek;
        String dayOfTheWeekString;

        //Setting background color white
        View titleLayout = view.findViewById(R.id.weekLayout);
        titleLayout.setBackgroundColor(weekLayoutBackgroundColor);

        final String[] weekDaysArray = new DateFormatSymbols(locale).getShortWeekdays();
        for (int i = 1; i < weekDaysArray.length; i++) {
            dayOfTheWeekString = weekDaysArray[i];
            dayOfTheWeekString = dayOfTheWeekString.substring(0, 3).toUpperCase();
            dayOfWeek = (TextView) view.findViewWithTag(DAY_OF_WEEK + getWeekIndex(i, currentCalendar));
            dayOfWeek.setText(dayOfTheWeekString);
            dayOfWeek.setTextColor(dayOfWeekTextColor);

            if (null != getCustomTypeface()) {
                dayOfWeek.setTypeface(getCustomTypeface());
            }
        }
    }

    private void setDaysInCalendar() {
        Calendar calendar = Calendar.getInstance(locale);
        calendar.setTime(currentCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.setFirstDayOfWeek(getFirstDayOfWeek());
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        // Calculate dayOfMonthIndex
        int dayOfMonthIndex = getWeekIndex(firstDayOfMonth, calendar);
        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        final Calendar startCalendar = (Calendar) calendar.clone();
        //Add required number of days
        startCalendar.add(Calendar.DATE, -(dayOfMonthIndex - 1));
        int monthEndIndex = 42 - (actualMaximum + dayOfMonthIndex - 1);

        DayView dayView;
        ViewGroup dayOfMonthContainer;
        for (int i = 1; i < 43; i++) {
            dayOfMonthContainer = (ViewGroup) view.findViewWithTag(DAY_OF_MONTH_CONTAINER + i);
            dayView = (DayView) view.findViewWithTag(DAY_OF_MONTH_TEXT + i);
            if (dayView == null)
                continue;

            //Apply the default styles
            dayOfMonthContainer.setOnClickListener(null);
            dayView.bind(startCalendar.getTime(), getDecorators());
            dayView.setVisibility(View.VISIBLE);

            if (null != getCustomTypeface()) {
                dayView.setTypeface(getCustomTypeface());
            }

            if (isSameMonth(calendar, startCalendar)) {
                dayOfMonthContainer.setOnClickListener(onDayOfMonthClickListener);
                dayView.setBackgroundColor(calendarBackgroundColor);
                dayView.setTextColor(dayOfWeekTextColor);
            } else {
                dayView.setBackgroundColor(disabledDayBackgroundColor);
                dayView.setTextColor(disabledDayTextColor);

                if (!isOverflowDateVisible())
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
        ViewGroup weekRow = (ViewGroup) view.findViewWithTag("weekRow6");
        dayView = (DayView) view.findViewWithTag("dayOfMonthText36");
        if (dayView.getVisibility() != VISIBLE) {
            weekRow.setVisibility(GONE);
        } else {
            weekRow.setVisibility(VISIBLE);
        }
    }


    public boolean isSameMonth(Calendar c1, Calendar c2) {
        if (c1 == null || c2 == null)
            return false;
        return (c1.get(Calendar.ERA) == c2.get(Calendar.ERA)
                && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
    }

    /**
     * <p>Checks if a calendar is today.</p>
     *
     * @param calendar the calendar, not altered, not null.
     * @return true if the calendar is today.
     * @throws IllegalArgumentException if the calendar is <code>null</code>
     */
    public static boolean isToday(Calendar calendar) {
        return isSameDay(calendar, Calendar.getInstance());
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null)
            throw new IllegalArgumentException("The dates must not be null");
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }


    private void clearDayOfTheMonthStyle(Date currentDate) {
        if (currentDate != null) {
            final Calendar calendar = getTodaysCalendar();
            calendar.setFirstDayOfWeek(getFirstDayOfWeek());
            calendar.setTime(currentDate);

            final DayView dayView = getDayOfMonthText(calendar);
            dayView.setBackgroundColor(calendarBackgroundColor);
            dayView.setTextColor(dayOfWeekTextColor);
        }
    }

    private DayView getDayOfMonthText(Calendar currentCalendar) {
        return (DayView) getView(DAY_OF_MONTH_TEXT, currentCalendar);
    }

    private int getDayIndexByDate(Calendar currentCalendar) {
        int monthOffset = getMonthOffset(currentCalendar);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int index = currentDay + monthOffset;
        return index;
    }

    private int getMonthOffset(Calendar currentCalendar) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(getFirstDayOfWeek());
        calendar.setTime(currentCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayWeekPosition = calendar.getFirstDayOfWeek();
        int dayPosition = calendar.get(Calendar.DAY_OF_WEEK);

        if (firstDayWeekPosition == 1) {
            return dayPosition - 1;
        } else {
            if (dayPosition == 1) {
                return 6;
            } else {
                return dayPosition - 2;
            }
        }
    }

    private int getWeekIndex(int weekIndex, Calendar currentCalendar) {
        int firstDayWeekPosition = currentCalendar.getFirstDayOfWeek();
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

    private View getView(String key, Calendar currentCalendar) {
        int index = getDayIndexByDate(currentCalendar);
        View childView = view.findViewWithTag(key + index);
        return childView;
    }

    private Calendar getTodaysCalendar() {
        Calendar currentCalendar = Calendar.getInstance(mContext.getResources().getConfiguration().locale);
        currentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        return currentCalendar;
    }

    @SuppressLint("DefaultLocale")
    public void refreshCalendar(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
        this.currentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        locale = mContext.getResources().getConfiguration().locale;

        // Set date title
        initializeTitleLayout();

        // Set weeks days titles
        initializeWeekLayout();

        // Initialize and set days in calendar
        setDaysInCalendar();
    }

    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public void markDayAsCurrentDay(Calendar calendar) {
        if (calendar != null && isToday(calendar)) {
            DayView dayOfMonth = getDayOfMonthText(calendar);
            dayOfMonth.setTextColor(currentDayOfMonth);
        }
    }

    public void markDayAsSelectedDay(Date currentDate) {
        final Calendar currentCalendar = getTodaysCalendar();
        currentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        currentCalendar.setTime(currentDate);

        // Clear previous marks
        clearDayOfTheMonthStyle(lastSelectedDay);

        // Store current values as last values
        storeLastValues(currentDate);

        // Mark current day as selected
        DayView view = getDayOfMonthText(currentCalendar);
        view.setBackgroundColor(selectedDayBackground);
        view.setTextColor(selectedDayTextColor);
    }

    private void storeLastValues(Date currentDate) {
        lastSelectedDay = currentDate;
    }

    public void setCalendarListener(CalendarListener calendarListener) {
        this.calendarListener = calendarListener;
    }

    private OnClickListener onDayOfMonthClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // Extract day selected
            ViewGroup dayOfMonthContainer = (ViewGroup) view;
            String tagId = (String) dayOfMonthContainer.getTag();
            tagId = tagId.substring(DAY_OF_MONTH_CONTAINER.length(), tagId.length());
            final TextView dayOfMonthText = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + tagId);

            // Fire event
            final Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(getFirstDayOfWeek());
            calendar.setTime(currentCalendar.getTime());
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dayOfMonthText.getText().toString()));
            markDayAsSelectedDay(calendar.getTime());

            //Set the current day color
            markDayAsCurrentDay(currentCalendar);

            if (calendarListener != null)
                calendarListener.onDateSelected(calendar.getTime());
        }
    };

    public List<DayDecorator> getDecorators() {
        return decorators;
    }

    public void setDecorators(List<DayDecorator> decorators) {
        this.decorators = decorators;
    }

    public boolean isOverflowDateVisible() {
        return isOverflowDateVisible;
    }

    public void setShowOverflowDate(boolean isOverFlowEnabled) {
        isOverflowDateVisible = isOverFlowEnabled;
    }

    public void setCustomTypeface(Typeface customTypeface) {
        this.customTypeface = customTypeface;
    }

    public Typeface getCustomTypeface() {
        return customTypeface;
    }

    public Calendar getCurrentCalendar() {
        return currentCalendar;
    }

    public interface CalendarListener {
        void onDateSelected(Date date);

        void onMonthChanged(Date time);
    }
}
