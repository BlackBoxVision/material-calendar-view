package com.android.support.calendar.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.support.calendar.R;
import com.android.support.calendar.exception.IllegalViewArgumentException;
import com.android.support.calendar.model.DayTime;
import com.android.support.calendar.util.CalendarUtility;

import java.util.Calendar;
import java.util.List;

import static com.android.support.calendar.exception.IllegalViewArgumentException.*;

/**
 * @author jonatan.salas
 */
public class AdapterView extends LinearLayout {
    private OnListItemSelectedListener onListItemSelectedListener;

    private DayTimeAdapter monthAdapter;
    private RecyclerView recyclerView;
    private View view;

    /**
     * Constructor with arguments. It only takes the Context as param.
     *
     * @param context the context used to inflate or get resources
     */
    public AdapterView(Context context) {
        this(context, null, 0);
    }

    /**
     * Constructor with arguments. It takes the Context as main param
     * and as second param an AttributeSet.
     *
     * @param context the context used to inflate or get resources
     * @param attrs   the attributes styled from a XML file
     */
    public AdapterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor with arguments. It takes the Context as main param
     * and as second param an AttributeSet, and as third param the defStyleAttr.
     *
     * @param context      the context used to inflate or get resources
     * @param attrs        the attributes styled from a XML file
     * @param defStyleAttr int resource used to get the Styles array
     */
    public AdapterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        view = View.inflate(getContext(), R.layout.adapter_view, this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
    }

    public void updateMonthsAdapter(@NonNull final Calendar calendar, int monthIndex) {
        final List<DayTime> list = CalendarUtility.obtainDayTimes(calendar, monthIndex);

        if (null != monthAdapter) {
            monthAdapter.setItems(list);
        } else {
            monthAdapter = new DayTimeAdapter(list);
        }

        recyclerView.setAdapter(monthAdapter);
        monthAdapter.notifyDataSetChanged();
    }

    public void setOnListItemSelectedListener(OnListItemSelectedListener onListItemSelected) {
        this.onListItemSelectedListener = onListItemSelected;
    }

    /**
     * @author jonatan.salas
     */
    private class DayTimeAdapter extends RecyclerView.Adapter<DayTimeAdapter.DayTimeViewHolder> {
        private List<DayTime> items;

        public DayTimeAdapter(@NonNull List<DayTime> items) {
            this.items = items;
        }

        @Override
        public DayTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View v = View.inflate(getContext(), R.layout.day_view, parent);
            return new DayTimeViewHolder(v);
        }

        @Override
        public void onBindViewHolder(DayTimeViewHolder holder, int position) {
            final DayTime dayTime = items.get(position);

            holder.textView.setClickable(true);
            holder.textView.setText(String.valueOf(dayTime.getDay()));
//            holder.textView.setTextSize(mAdapterViewFontSize);
//            holder.textView.setTextColor(mAdapterViewTextColor);

            if (!dayTime.isCurrentMonth()) {
                holder.textView.setTypeface(Typeface.DEFAULT_BOLD);
//                holder.textView.setBackgroundColor(mDisabledBackgroundColor);
//                holder.textView.setTextColor(mDisabledTextColor);
                holder.textView.setEnabled(false);
                holder.textView.setClickable(false);
            }

            if (dayTime.isWeekend() && dayTime.isCurrentMonth()) {
//                holder.mDayView.setBackgroundColor(mWeekendBackgroundColor);
//                holder.textView.setTextColor(mWeekendTextColor);
                holder.textView.setTypeface(Typeface.DEFAULT_BOLD);
                holder.textView.setEnabled(true);
                holder.textView.setClickable(true);
            }

            if (dayTime.isCurrentDay() && dayTime.isCurrentMonth()) {
//                holder.textView.setBackgroundColor(mCurrentBackgroundColor);"onListItemSelectedListener can't be null!"
//                holder.textView.setTextColor(mCurrentTextColor);
                holder.textView.setEnabled(true);
                holder.textView.setClickable(true);
            }

            holder.textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onListItemSelectedListener) {
                        onListItemSelectedListener.onItemSelected(v, dayTime);
                    } else {
                        throw new IllegalViewArgumentException(ITEM_SELECTED_LISTENER_NOT_NULL_MESSAGE);
                    }
                }
            });

            holder.textView.invalidate();
        }

        @Override
        public int getItemCount() {
            return (null != items) ? items.size() : 0;
        }

        public void setItems(List<DayTime> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        protected class DayTimeViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;

            public DayTimeViewHolder(@NonNull View view) {
                super(view);
                this.textView = (TextView) view.findViewById(R.id.day_view);
            }
        }
    }

    /**
     * @author jonatan.salas
     */
    public interface OnListItemSelectedListener {

        void onItemSelected(@NonNull View view, @NonNull DayTime dayTime);
    }
}
