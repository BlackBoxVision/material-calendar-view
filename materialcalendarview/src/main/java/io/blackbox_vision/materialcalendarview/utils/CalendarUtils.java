package io.blackbox_vision.materialcalendarview.utils;

import android.support.annotation.NonNull;
import android.util.MonthDisplayHelper;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.blackbox_vision.materialcalendarview.data.Day;


@SuppressWarnings("all")
public final class CalendarUtils {

    private CalendarUtils() { }

    public static boolean isToday(Calendar calendar) {
        return isSameDay(calendar, Calendar.getInstance());
    }

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

    public static String getCurrentMonth(int monthIndex) {
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        final DateFormatSymbols dateFormat = new DateFormatSymbols(Locale.getDefault());
        calendar.add(Calendar.MONTH, monthIndex);

        return dateFormat.getMonths()[calendar.get(Calendar.MONTH)];
    }

    public static boolean isWeekend(Calendar calendar) {
        int position = calendar.get(Calendar.DAY_OF_WEEK);
        return position == Calendar.SATURDAY || position == Calendar.SUNDAY;
    }

    public static boolean isSameYear(@NonNull Calendar c1, @NonNull Calendar c2) {
        return (c1.get(Calendar.ERA) == c2.get(Calendar.ERA)
                && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)));
    }

    public static boolean isSameMonth(@NonNull Calendar c1, @NonNull Calendar c2) {
        return (c1.get(Calendar.ERA) == c2.get(Calendar.ERA)
                && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)));
    }

    public static boolean isSameDay(@NonNull Calendar cal1, @NonNull Calendar cal2) {
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
                && (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)));
    }

    public static List<String> getShortWeekDays(Locale locale) {
        final String[] array = new DateFormatSymbols(locale).getShortWeekdays();
        return Arrays.asList(array);
    }

    public static int calculateWeekIndex(final Calendar calendar, final int weekIndex) {
        final int firstDayWeekPosition = calendar.getFirstDayOfWeek();

        if (firstDayWeekPosition == 1) {
            return weekIndex;
        } else {
            return (weekIndex == 1)? 7 : weekIndex - 1;
        }
    }

    public static List<Day> obtainDays(final Calendar calendar, final int index) {
        int year = getYear(calendar);
        int month = getMonth(calendar);
        int firstDayOfWeek = getFirstDayOfWeek(calendar);

        final MonthDisplayHelper helper = new MonthDisplayHelper(year, month, firstDayOfWeek);
        final List<Day> days = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            int n[] = helper.getDigitsForRow(i);

            for (int j = 0; j < 7; j++) {
                Day d = new Day();

                if (helper.isWithinCurrentMonth(i, j)) {
                    Calendar c = Calendar.getInstance(Locale.getDefault());
                    calendar.set(Calendar.DAY_OF_MONTH, n[j]);
                    calendar.add(Calendar.MONTH, index);

                    int m = getMonth(c);
                    int y = getYear(c);

                    if (n[j] == calendar.get(Calendar.DAY_OF_MONTH) && isWeekend(c) && index == 0) {
                        d.setDay(n[j])
                         .setMonth(m)
                         .setYear(y)
                         .setCurrentDay(false)
                         .setCurrentMonth(true)
                         .setCurrentYear(true)
                         .setWeekend(true);

                    } else if (n[j] == c.get(Calendar.DAY_OF_MONTH) && index == 0) {
                        d.setDay(n[j])
                         .setMonth(m)
                         .setYear(y)
                         .setCurrentDay(true)
                         .setCurrentMonth(true)
                         .setCurrentYear(true)
                         .setWeekend(false);

                    } else if (isWeekend(calendar)) {
                        d.setDay(n[j])
                         .setMonth(m)
                         .setYear(y)
                         .setCurrentDay(false)
                         .setCurrentMonth(true)
                         .setCurrentYear(true)
                         .setWeekend(true);

                    } else {
                        d.setDay(n[j])
                         .setMonth(m)
                         .setYear(y)
                         .setCurrentDay(false)
                         .setCurrentMonth(true)
                         .setCurrentYear(true)
                         .setWeekend(false);
                    }

                } else {
                    Calendar c = Calendar.getInstance(Locale.getDefault());

                    c.set(Calendar.DAY_OF_MONTH, n[j]);
                    c.add(Calendar.MONTH, index);

                    d.setDay(n[j])
                     .setMonth(getMonth(c))
                     .setYear(getYear(c))
                     .setCurrentDay(false)
                     .setCurrentMonth(false)
                     .setCurrentYear(true)
                     .setWeekend(false);
                }

                days.add(d);
            }
        }

        return days;
    }

    public static String getDateTitle(Locale locale, int index) {
        final String[] months = new DateFormatSymbols(locale).getMonths();
        final Calendar calendar = Calendar.getInstance(locale);

        calendar.add(Calendar.MONTH, index);

        final String title = months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);

        return title.toUpperCase(Locale.getDefault());
    }

    public static int getFirstDayOfWeek(Calendar calendar) {
        return calendar.getFirstDayOfWeek();
    }

    public static int getMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }
}

