package io.blackbox_vision.materialcalendarview.internal.data;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public final class Day {
    private int day;
    private int month;
    private int year;

    private boolean currentDay;
    private boolean currentMonth;
    private boolean currentYear;
    private boolean weekend;

    public int getDay() {
        return day;
    }

    public Day setDay(int day) {
        this.day = day;
        return this;
    }

    public int getMonth() {
        return month;
    }

    public Day setMonth(int month) {
        this.month = month;
        return this;
    }

    public int getYear() {
        return year;
    }

    public Day setYear(int year) {
        this.year = year;
        return this;
    }

    public boolean isCurrentDay() {
        return currentDay;
    }

    public Day setCurrentDay(boolean currentDay) {
        this.currentDay = currentDay;
        return this;
    }

    public boolean isCurrentMonth() {
        return currentMonth;
    }

    public Day setCurrentMonth(boolean currentMonth) {
        this.currentMonth = currentMonth;
        return this;
    }

    public boolean isCurrentYear() {
        return currentYear;
    }

    public Day setCurrentYear(boolean currentYear) {
        this.currentYear = currentYear;
        return this;
    }

    public boolean isWeekend() {
        return weekend;
    }

    public Day setWeekend(boolean weekend) {
        this.weekend = weekend;
        return this;
    }

    public Calendar toCalendar() {
        Calendar c = Calendar.getInstance(Locale.getDefault());

        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);

        return c;
    }

    public Calendar toCalendar(Locale locale) {
        Calendar c = Calendar.getInstance(locale);

        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);

        return c;
    }

    public Date toDate() {
        return toCalendar().getTime();
    }

    public Date toDate(Locale locale) {
        return toCalendar(locale).getTime();
    }
}
