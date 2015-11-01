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
public class TestSwipeLeft extends ActivityInstrumentationTestCase2<MainActivity> {
    private static final int CURRENT_MILLIS = 5000;
    private CalendarView mCalendarView;
    private Locale mLocale;

    public TestSwipeLeft() {
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

    public void testSwipeLeft() {
        Rect rect1 = new Rect();
        mCalendarView.getGlobalVisibleRect(rect1);

        float fromX = rect1.centerX();
        float toX = rect1.centerX() + (rect1.width() / 4);
        float fromY = rect1.centerY();
        float toY = rect1.centerY();
        int stepCount = 10;

        TouchUtils.drag(this, fromX, toX, fromY, toY, stepCount);

        try {
            Thread.sleep(CURRENT_MILLIS);
            assertEquals(getCurrentMonth(Calendar.OCTOBER), mCalendarView.getCurrentMonth());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getCurrentMonth(int position) {
        return new DateFormatSymbols(mLocale).getMonths()[position].toUpperCase();
    }
}
