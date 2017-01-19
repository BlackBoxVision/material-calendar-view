package io.blackbox_vision.materialcalendarview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import io.blackbox_vision.materialcalendarview.internal.data.Day;


public final class DayView extends TextView {
    private Day day;

    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDay(Day day) {
        setText(String.valueOf(day.getDay()));
        this.day = day;
        invalidate();
    }

    public Day getDay() {
        return day;
    }
}
