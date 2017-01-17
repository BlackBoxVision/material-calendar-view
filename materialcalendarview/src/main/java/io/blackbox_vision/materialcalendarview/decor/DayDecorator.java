package io.blackbox_vision.materialcalendarview.decor;

import android.support.annotation.NonNull;

import io.blackbox_vision.materialcalendarview.view.DayView;


public interface DayDecorator {

    void decorate(@NonNull DayView cell);
}