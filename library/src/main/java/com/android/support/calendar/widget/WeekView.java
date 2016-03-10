package com.android.support.calendar.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.android.support.calendar.R;

/**
 * @author jonatan.salas
 */
public class WeekView extends LinearLayout {
    private View view;

    /**
     * Constructor with arguments. It only takes the Context as param.
     *
     * @param context the context used to inflate or get resources
     */
    public WeekView(Context context) {
        this(context, null, 0);
    }

    /**
     * Constructor with arguments. It takes the Context as main param
     * and as second param an AttributeSet.
     *
     * @param context the context used to inflate or get resources
     * @param attrs the attributes styled from a XML file
     */
    public WeekView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor with arguments. It takes the Context as main param
     * and as second param an AttributeSet, and as third param the defStyleAttr.
     *
     * @param context the context used to inflate or get resources
     * @param attrs the attributes styled from a XML file
     * @param defStyleAttr int resource used to get the Styles array
     */
    public WeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.week_view, this, true);
    }
}
