package com.samsistemas.materialcalendar.activity;

import android.graphics.Rect;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;

import com.samsistemas.calendarview.widget.CalendarView;
import com.samsistemas.materialcalendar.MainActivity;
import com.samsistemas.materialcalendar.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author jonatan.salas
 */
public class TestSwipeRight extends ActivityInstrumentationTestCase2<MainActivity> {
    private static final int CURRENT_MILLIS = 5000;
    private CalendarView mCalendarView;
    private Locale mLocale;

    public TestSwipeRight() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);

        MainActivity mainActivity = getActivity();
        mCalendarView = (CalendarView) mainActivity.findViewById(R.id.calendar_view);
        mLocale = mainActivity.getResources().getConfiguration().locale;
    }

    public void testSwipeRight() {
        Rect rect2 = new Rect();
        mCalendarView.getGlobalVisibleRect(rect2);

        float fromX = rect2.centerX();
        float toX = rect2.centerX() - (rect2.width() / 4);
        float fromY = rect2.centerY();
        float toY = rect2.centerY();
        int stepCount = 10;

        TouchUtils.drag(this, fromX, toX, fromY, toY, stepCount);

        try {
            Thread.sleep(CURRENT_MILLIS);
            assertEquals(getCurrentMonth(Calendar.DECEMBER), mCalendarView.getCurrentMonth());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getCurrentMonth(int position) {
        return new DateFormatSymbols(mLocale).getMonths()[position].toUpperCase();
    }
}
