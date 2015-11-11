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
package com.android.support.v8.util;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * @author jonatan.salas
 */
public class CalendarUtil {

    /**
     *
     * @param calendar
     * @return
     */
    public static boolean isWeekend(Calendar calendar) {
        int position = calendar.get(Calendar.DAY_OF_WEEK);
        return position == Calendar.SATURDAY || position == Calendar.SUNDAY;
    }

    /**
     *
     * @param c1
     * @param c2
     * @return
     */
    public static boolean isSameYear(@NonNull Calendar c1, @NonNull Calendar c2) {
        return (c1.get(Calendar.ERA) == c2.get(Calendar.ERA) &&
               (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)));
    }

    /**
     *
     * @param c1
     * @param c2
     * @return
     */
    public static boolean isSameMonth(@NonNull Calendar c1, @NonNull Calendar c2) {
        return (c1.get(Calendar.ERA) == c2.get(Calendar.ERA) &&
               (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) &&
               (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)));
    }

    /**
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameDay(@NonNull Calendar cal1, @NonNull Calendar cal2) {
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
               (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) &&
               (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)));
    }

    /**
     *
     * @param weekIndex
     * @param calendar
     * @return
     */
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
}
