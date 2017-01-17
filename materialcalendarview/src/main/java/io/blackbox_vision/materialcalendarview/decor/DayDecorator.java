package io.blackbox_vision.materialcalendarview.decor;

import android.support.annotation.NonNull;

import io.blackbox_vision.materialcalendarview.view.DayView;

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