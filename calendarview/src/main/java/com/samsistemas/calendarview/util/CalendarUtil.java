package com.samsistemas.calendarview.util;

import android.content.Context;

import java.util.Calendar;

/**
 * @author jonatan.salas
 */
public class CalendarUtil {

    /**
     *
     * @param c1
     * @param c2
     * @return
     */
    public static boolean isSameMonth(Calendar c1, Calendar c2) {
        return !(c1 == null || c2 == null) &&
               (c1.get(Calendar.ERA) == c2.get(Calendar.ERA) &&
               (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) &&
               (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)));
    }

    /**
     *
     * @param calendar
     * @return
     */
    public static boolean isToday(Calendar calendar) {
        return isSameDay(calendar, Calendar.getInstance());
    }

    /**
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null)
            throw new IllegalArgumentException("The dates must not be null");
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
               (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) &&
               (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)));
    }

    /**
     *
     * @param context
     * @param firstDayOfWeek
     * @return
     */
    public static Calendar getTodayCalendar(Context context, int firstDayOfWeek) {
        Calendar currentCalendar = Calendar.getInstance(context.getResources().getConfiguration().locale);
        currentCalendar.setFirstDayOfWeek(firstDayOfWeek);

        return currentCalendar;
    }

    /**
     *
     * @param currentCalendar
     * @param firstDayOfWeek
     * @return
     */
    public static int getMonthOffset(Calendar currentCalendar, int firstDayOfWeek) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(firstDayOfWeek);
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

    /**
     *
     * @param weekIndex
     * @param calendar
     * @return
     */
    public static int getWeekIndex(int weekIndex, Calendar calendar) {
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
}
