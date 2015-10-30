package com.samsistemas.calendarview.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.samsistemas.calendarview.decor.DayDecorator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DayView extends TextView {
    private Date date;
    private List<DayDecorator> decorators;

    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode())
                return;
        }
    }

    public void bind(Date date, List<DayDecorator> decorators) {
        this.date = date;
        this.decorators = decorators;

        final SimpleDateFormat df = new SimpleDateFormat("d");
        int day = Integer.parseInt(df.format(date));
        setText(String.valueOf(day));
    }

    public void decorate() {
        //Set custom decorators
        if (null != decorators) {
            for (DayDecorator decorator : decorators) {
                decorator.decorate(this);
            }
        }
    }

    public Date getDate() {
        return date;
    }
}
