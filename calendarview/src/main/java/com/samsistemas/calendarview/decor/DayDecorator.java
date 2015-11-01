package com.samsistemas.calendarview.decor;

import android.support.annotation.NonNull;

import com.samsistemas.calendarview.widget.DayView;

/**
 * @author jonatan.salas
 */
public interface DayDecorator {

    /**
     *
     * @param cell
     */
    void decorate(@NonNull DayView cell);
}