package io.blackbox_vision.materialcalendarview.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

public class DayView extends TextView {

    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode()) {
                return;
            }
        }
    }
}
