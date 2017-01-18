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
package materialcalendarview2.util;

import android.support.annotation.NonNull;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import materialcalendarview2.data.callback.DayTimeCallback;
import materialcalendarview2.data.model.DayTime;

import static materialcalendarview2.util.ThreadUtils.runInBackground;

/**
 * @author jonatan.salas
 */
public final class CalendarUtils {

    private CalendarUtils() { }

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

    public static List<DayTime> obtainDayTimes(final Calendar calendar, final int index) {
        return runInBackground(new DayTimeCallback(calendar, index));
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
