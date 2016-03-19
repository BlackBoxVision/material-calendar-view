package com.jonatansalas.materialcalendarview.callback;

import android.util.MonthDisplayHelper;

import com.jonatansalas.materialcalendarview.model.DayTime;
import com.jonatansalas.materialcalendarview.util.CalendarUtility;
import com.jonatansalas.materialcalendarview.util.ThreadUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author jonatan.salas
 */
public class FutureCallback implements ThreadUtility.CallBack<List<DayTime>> {
    private Calendar calendar;
    private int index;

    public FutureCallback(final Calendar calendar, final int index) {
        this.calendar = calendar;
        this.index = index;
    }

    @Override
    public List<DayTime> execute() {
        final int year = CalendarUtility.getYear(calendar);
        final int month = CalendarUtility.getMonth(calendar);
        final int firstDayOfWeek = CalendarUtility.getFirstDayOfWeek(calendar);

        final MonthDisplayHelper helper = new MonthDisplayHelper(year, month, firstDayOfWeek);
        final List<DayTime> dayTimeList = new ArrayList<>(42);

        //TODO JS: Evaluate this code.
        for (int i = 0; i < 6; i++) {
            int n[] = helper.getDigitsForRow(i);

            for (int d = 0; d < 7; d++) {
                if (helper.isWithinCurrentMonth(i, d)) {
                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.set(Calendar.DAY_OF_MONTH, n[d]);
                    calendar.add(Calendar.MONTH, index);

                    int m = CalendarUtility.getMonth(calendar);
                    int y = CalendarUtility.getYear(calendar);

                    if (n[d] == calendar.get(Calendar.DAY_OF_MONTH) &&
                            CalendarUtility.isWeekend(calendar) && index == 0) {
                        DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(m)
                                .setYear(y)
                                .setCurrentDay(false)
                                .setCurrentMonth(true)
                                .setCurrentYear(true)
                                .setWeekend(true)
                                .setEventList(null);

                        dayTimeList.add(dayTime);
                    } else if (n[d] == calendar.get(Calendar.DAY_OF_MONTH) && index == 0) {
                        DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(m)
                                .setYear(y)
                                .setCurrentDay(true)
                                .setCurrentMonth(true)
                                .setCurrentYear(true)
                                .setWeekend(false)
                                .setEventList(null);

                        dayTimeList.add(dayTime);
                    } else if (CalendarUtility.isWeekend(calendar)) {
                        DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(m)
                                .setYear(y)
                                .setCurrentDay(false)
                                .setCurrentMonth(true)
                                .setCurrentYear(true)
                                .setWeekend(true)
                                .setEventList(null);

                        dayTimeList.add(dayTime);
                    } else {
                        final DayTime dayTime = new DayTime()
                                .setDay(n[d])
                                .setMonth(m)
                                .setYear(y)
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
                    calendar.add(Calendar.MONTH, index);


                    int m = CalendarUtility.getMonth(calendar);
                    int y = CalendarUtility.getYear(calendar);

                    DayTime dayTime = new DayTime()
                            .setDay(n[d])
                            .setMonth(m)
                            .setYear(y)
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
}
