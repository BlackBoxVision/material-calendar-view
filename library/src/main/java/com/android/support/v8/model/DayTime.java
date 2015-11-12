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
package com.android.support.v8.model;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author jonatan.salas
 */
public class DayTime {
    private int day;
    private int month;
    private int year;

    private boolean isCurrentDay;
    private boolean isCurrentMonth;
    private boolean isCurrentYear;
    private boolean isWeekend;

    @Nullable
    private List<Event> eventList;

    public DayTime() { }

    /**
     *
     * @param day
     */
    public DayTime setDay(int day) {
        this.day = day;
        return this;
    }

    /**
     *
     * @param month
     */
    public DayTime setMonth(int month) {
        this.month = month;
        return this;
    }

    /**
     *
     * @param year
     */
    public DayTime setYear(int year) {
        this.year = year;
        return this;
    }

    /**
     *
     * @param isCurrentDay
     */
    public DayTime setIsCurrentDay(boolean isCurrentDay) {
        this.isCurrentDay = isCurrentDay;
        return this;
    }

    /**
     *
     * @param isCurrentMonth
     */
    public DayTime setIsCurrentMonth(boolean isCurrentMonth) {
        this.isCurrentMonth = isCurrentMonth;
        return this;
    }

    /**
     *
     * @param isCurrentYear
     */
    public DayTime setIsCurrentYear(boolean isCurrentYear) {
        this.isCurrentYear = isCurrentYear;
        return this;
    }

    /**
     *
     * @param isWeekend
     */
    public DayTime setIsWeekend(boolean isWeekend) {
        this.isWeekend = isWeekend;
        return this;
    }

    /**
     *
     * @param eventList
     */
    public DayTime setEventList(@Nullable List<Event> eventList) {
        this.eventList = eventList;
        return this;
    }

    /**
     *
     * @return
     */
    public int getDay() {
        return day;
    }

    /**
     *
     * @return
     */
    public int getMonth() {
        return month;
    }

    /**
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     *
     * @return
     */
    public boolean isCurrentDay() {
        return isCurrentDay;
    }

    /**
     *
     * @return
     */
    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    /**
     *
     * @return
     */
    public boolean isCurrentYear() {
        return isCurrentYear;
    }

    /**
     *
     * @return
     */
    public boolean isWeekend() {
        return isWeekend;
    }

    /**
     *
     * @return
     */
    @Nullable
    public List<Event> getEventList() {
        return eventList;
    }
}
