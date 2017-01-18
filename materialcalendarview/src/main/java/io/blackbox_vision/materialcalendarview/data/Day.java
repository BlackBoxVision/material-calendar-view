package io.blackbox_vision.materialcalendarview.data;


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
}
