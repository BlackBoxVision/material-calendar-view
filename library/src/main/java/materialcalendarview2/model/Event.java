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

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Locale;

public class Event {
    @NonNull
    private Calendar eventDate = Calendar.getInstance(Locale.getDefault());
    @NonNull
    private String description = "";
    private int duration;

    public Event() { }

    public Event setEventDate(@NonNull Calendar eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public Event setDescription(@NonNull String description) {
        this.description = description;
        return this;
    }

    public Event setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    @NonNull
    public Calendar getEventDate() {
        return eventDate;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }
}
