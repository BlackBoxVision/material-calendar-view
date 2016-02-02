package com.samsistemas.calendarview.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.samsistemas.calendarview.decor.DayDecorator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayView extends TextView {
    private List<DayDecorator> mDayDecoratorList;
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
        this.mDayDecoratorList = decorators;
        this.mDate = date;

        final SimpleDateFormat df = new SimpleDateFormat("d", Locale.getDefault());
        int day = Integer.parseInt(df.format(date));
        setText(String.valueOf(day));
    }

    public void decorate() {
        //Set custom mDayDecoratorList
        if (null != mDayDecoratorList) {
            for (DayDecorator decorator : mDayDecoratorList) {
                decorator.decorate(this);
            }
        }
    }

    public Date getDate() {
        return mDate;
    }
}
