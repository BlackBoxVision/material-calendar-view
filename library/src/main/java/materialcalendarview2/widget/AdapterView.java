package materialcalendarview2.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Calendar;

import materialcalendarview2.R;
import materialcalendarview2.adapter.DayTimeAdapter;

import static materialcalendarview2.util.CalendarUtil.obtainDayTimes;

/**
 * The View that contains the Adapter with the month data.
 *
 * @author jonatan.salas
 */
public class AdapterView extends LinearLayout {
    private DayTimeAdapter monthAdapter;
    private RecyclerView recyclerView;
    private View view;

    private int monthIndex = 0;

    /**
     * Constructor with arguments. It only takes the Context as param.
     *
     * @param context the context used to inflate or get resources
     */
    public AdapterView(Context context) {
        this(context, null);
    }

    /**
     * Constructor with arguments. It takes the Context as main param
     * and as second param an AttributeSet.
     *
     * @param context the context used to inflate or get resources
     * @param attrs   the attributes styled from a XML file
     */
    public AdapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Constructor with arguments. It takes the Context as main param
     * and as second param an AttributeSet, and as third param the defStyleAttr.
     *
     * @param context      the context used to inflate or get resources
     * @param attrs        the attributes styled from a XML file
     * @param defStyleAttr int resource used to get the Styles array
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdapterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        monthAdapter = new DayTimeAdapter(getContext());

        view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view, this, true);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);

        recyclerView.invalidate();
    }

    public void init(@NonNull Calendar calendar, int monthIndex) {
        setMonthIndex(monthIndex);

        recyclerView.setAdapter(monthAdapter);
        monthAdapter.setItems(obtainDayTimes(calendar, monthIndex));

        recyclerView.invalidate();
    }

    public DayTimeAdapter adapter() {
        return monthAdapter;
    }

    public void setMonthIndex(int monthIndex) {
        this.monthIndex = monthIndex;
    }

    public int getMonthIndex() {
        return monthIndex;
    }
}
