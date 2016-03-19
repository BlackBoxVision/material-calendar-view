package com.jonatansalas.materialcalendarview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.desmond.ripple.RippleCompat;

/**
 * @author jonatan.salas
 */
public class DayView extends TextView {

    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void drawRipples() {
        RippleCompat.init(getContext().getApplicationContext());
        RippleCompat.apply(this, this.getCurrentTextColor());
    }
}
