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
package com.android.support.calendar.util;

import android.support.annotation.NonNull;
import android.util.MonthDisplayHelper;

import com.android.support.calendar.model.DayTime;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author jonatan.salas
 */
public final class CalendarUtility {

    private CalendarUtility() { }

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

    public static int calculateWeekIndex(int weekIndex, Calendar calendar) {
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

    public static List<DayTime> obtainDayTimes(Calendar currentCalendar, int monthIndex) {
        MonthDisplayHelper displayHelper = new MonthDisplayHelper(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.getFirstDayOfWeek());
        final List<DayTime> dayTimeList = new ArrayList<>(42);

        for (int i = 0; i < 6; i++) {
            int n[] = displayHelper.getDigitsForRow(i);

            for (int d = 0; d < 7; d++) {
                if (displayHelper.isWithinCurrentMonth(i, d)) {
                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.set(Calendar.DAY_OF_MONTH, n[d]);
                    calendar.add(Calendar.MONTH, monthIndex);

                    if (n[d] == currentCalendar.get(Calendar.DAY_OF_MONTH) && CalendarUtility.isWeekend(calendar) && monthIndex == 0) {
                        final DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(currentCalendar.get(Calendar.MONTH))
                                .setYear(currentCalendar.get(Calendar.YEAR))
                                .setCurrentDay(true)
                                .setCurrentMonth(true)
                                .setCurrentYear(true)
                                .setWeekend(true)
                                .setEventList(null);

                        dayTimeList.add(dayTime);
                    } else if (n[d] == currentCalendar.get(Calendar.DAY_OF_MONTH) && monthIndex == 0) {
                        final DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(currentCalendar.get(Calendar.MONTH))
                                .setYear(currentCalendar.get(Calendar.YEAR))
                                .setCurrentDay(true)
                                .setCurrentMonth(true)
                                .setCurrentYear(true)
                                .setWeekend(false)
                                .setEventList(null);

                        dayTimeList.add(dayTime);
                    } else if (CalendarUtility.isWeekend(calendar)) {
                        final DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(currentCalendar.get(Calendar.MONTH))
                                .setYear(currentCalendar.get(Calendar.YEAR))
                                .setCurrentDay(false)
                                .setCurrentMonth(true)
                                .setCurrentYear(true)
                                .setWeekend(true)
                                .setEventList(null);

                        dayTimeList.add(dayTime);
                    } else {
                        final DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(currentCalendar.get(Calendar.MONTH))
                                .setYear(currentCalendar.get(Calendar.YEAR))
                                .setCurrentDay(false)
                                .setCurrentMonth(true)
                                .setCurrentYear(true)
                                .setWeekend(false)
                                .setEventList(null);

                        dayTimeList.add(dayTime);
                    }

                } else {
                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.set(Calendar.DAY_OF_MONTH, n[d]);
                    calendar.add(Calendar.MONTH, monthIndex);

                    final DayTime dayTime = new DayTime()
                            .setDay(n[d])
                            .setMonth(calendar.get(Calendar.MONTH))
                            .setYear(calendar.get(Calendar.YEAR))
                            .setCurrentDay(false)
                            .setCurrentMonth(false)
                            .setCurrentYear(true)
                            .setWeekend(false)
                            .setEventList(null);

                    dayTimeList.add(dayTime);
                }
            }
        }

        return dayTimeList;
    }

    public static String getDateTitle(int monthIndex) {
        final DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());

        calendar.add(Calendar.MONTH, monthIndex);

        return dfs.getMonths()[monthIndex] + " " + calendar.get(Calendar.YEAR);
    }
}
