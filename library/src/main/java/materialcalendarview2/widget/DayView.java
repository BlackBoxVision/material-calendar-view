package materialcalendarview2.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.desmond.ripple.RippleCompat;

import materialcalendarview2.model.DayTime;

/**
 * @author jonatan.salas
 */
public class DayView extends TextView {
    private DayTime dayTime;

    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        RippleCompat.init(context.getApplicationContext());
    }

    public void setDayTime(@NonNull DayTime dayTime) {
        this.dayTime = dayTime;
    }

    public void drawRipples() {
        RippleCompat.apply(this, getCurrentTextColor());
    }

    public void drawRipples(Integer color) {
        RippleCompat.apply(this, color);
    }

    public void drawRipples(@ColorRes int colorId) {
        final int color = ContextCompat.getColor(getContext(), colorId);
        RippleCompat.apply(this, color);
    }
}
