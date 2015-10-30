package com.samsistemas.calendarview.decor;

import android.support.annotation.NonNull;

import com.samsistemas.calendarview.widget.DayView;

public interface DayDecorator {

    void decorate(@NonNull DayView cell);
}