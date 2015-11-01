package com.samsistemas.calendarview.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.samsistemas.calendarview.R;
import com.samsistemas.calendarview.Styleables;

/**
 * @author jonatan.salas
 */
public class AttributeUtil implements Styleables {

    /**
     *
     * @param context
     * @param attrs
     * @return
     */
    public static int[] getAttributes(@NonNull Context context, AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCalendarView, 0, 0);

        try {
            final int calendarBackgroundColor = typedArray.getColor(
                    CALENDAR_BACKGROUND_COLOR,
                    ContextCompat.getColor(context, R.color.white)
            );

            final int calendarTitleBackgroundColor = typedArray.getColor(
                    TITLE_BACKGROUND_COLOR,
                    ContextCompat.getColor(context, R.color.white)
            );

            final int calendarTitleTextColor = typedArray.getColor(
                    TITLE_TEXT_COLOR,
                    ContextCompat.getColor(context, R.color.black)
            );

            final int weekLayoutBackgroundColor = typedArray.getColor(
                    WEEK_BACKGROUND_COLOR,
                    ContextCompat.getColor(context, R.color.white)
            );

            final int dayOfWeekTextColor = typedArray.getColor(
                    DAY_TEXT_COLOR,
                    ContextCompat.getColor(context, R.color.black)
            );

            final int disabledDayBackgroundColor = typedArray.getColor(
                    DISABLE_DAY_BACKGROUND_COLOR,
                    ContextCompat.getColor(context, R.color.day_disabled_background_color)
            );

            final int disabledDayTextColor = typedArray.getColor(
                    DISABLE_DAY_TEXT_COLOR,
                    ContextCompat.getColor(context, R.color.day_disabled_text_color)
            );

            final int selectedDayBackground = typedArray.getColor(
                    SELECTED_DAY_BACKGROUND_COLOR,
                    ContextCompat.getColor(context, R.color.selected_day_background)
            );

            final int selectedDayTextColor = typedArray.getColor(
                    SELECTED_DAY_TEXT_COLOR,
                    ContextCompat.getColor(context, R.color.white)
            );

            final int currentDayOfMonth = typedArray.getColor(
                    CURRENT_DAY_COLOR,
                    ContextCompat.getColor(context, R.color.current_day_of_month)
            );

            return new int[] {
                    calendarBackgroundColor,
                    calendarTitleBackgroundColor,
                    calendarTitleTextColor,
                    weekLayoutBackgroundColor,
                    dayOfWeekTextColor,
                    disabledDayBackgroundColor,
                    disabledDayTextColor,
                    selectedDayBackground,
                    selectedDayTextColor,
                    currentDayOfMonth
            };

        } finally {
            typedArray.recycle();
        }
    }
}
