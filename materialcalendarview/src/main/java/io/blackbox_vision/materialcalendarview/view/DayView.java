package io.blackbox_vision.materialcalendarview.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import io.blackbox_vision.materialcalendarview.decor.DayDecorator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DayView extends TextView {
    private List<DayDecorator> dayDecoratorList;
    private Date mDate;

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

    public void bind(Date date, List<DayDecorator> decorators) {
        this.dayDecoratorList = decorators;
        this.mDate = date;

        final SimpleDateFormat df = new SimpleDateFormat("d", Locale.getDefault());
        int day = Integer.parseInt(df.format(date));
        setText(String.valueOf(day));
    }

    public void decorate() {
        //Set custom dayDecoratorList
        if (null != dayDecoratorList) {
            for (DayDecorator decorator : dayDecoratorList) {
                decorator.decorate(this);
            }
        }
    }

    public Date getDate() {
        return mDate;
    }
}
