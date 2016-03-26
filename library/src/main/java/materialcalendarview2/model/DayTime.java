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
package materialcalendarview2.model;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author jonatan.salas
 */
public class DayTime {
    private int day;
    private int month;
    private int year;

    private boolean currentDay;
    private boolean currentMonth;
    private boolean currentYear;
    private boolean weekend;

    @Nullable
    private List<Event> eventList;

    public DayTime() { }

    public DayTime setDay(int day) {
        this.day = day;
        return this;
    }

    public DayTime setMonth(int month) {
        this.month = month;
        return this;
    }

    public DayTime setYear(int year) {
        this.year = year;
        return this;
    }

    public DayTime setCurrentDay(boolean isCurrentDay) {
        this.currentDay = isCurrentDay;
        return this;
    }

    public DayTime setCurrentMonth(boolean isCurrentMonth) {
        this.currentMonth = isCurrentMonth;
        return this;
    }

    public DayTime setCurrentYear(boolean isCurrentYear) {
        this.currentYear = isCurrentYear;
        return this;
    }

    public DayTime setWeekend(boolean isWeekend) {
        this.weekend = isWeekend;
        return this;
    }

    public DayTime setEventList(@Nullable List<Event> eventList) {
        this.eventList = eventList;
        return this;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public boolean isCurrentDay() {
        return currentDay;
    }

    public boolean isCurrentMonth() {
        return currentMonth;
    }

    public boolean isCurrentYear() {
        return currentYear;
    }

    public boolean isWeekend() {
        return weekend;
    }

    @Nullable
    public List<Event> getEventList() {
        return eventList;
    }
}
