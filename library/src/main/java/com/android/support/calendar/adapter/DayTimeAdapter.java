package com.android.support.calendar.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.support.calendar.R;
import com.android.support.calendar.exception.IllegalViewArgumentException;
import com.android.support.calendar.model.DayTime;

import java.util.List;

import static com.android.support.calendar.exception.IllegalViewArgumentException.*;

/**
 * @author jonatan.salas
 */
public class DayTimeAdapter extends RecyclerView.Adapter<DayTimeAdapter.DayTimeViewHolder> {
    private OnListItemClickListener onListItemClickListener;
    private OnListItemLongClickListener onListItemLongClickListener;
    private OnStyleChangeListener onStyleChangeListener;

    private Context context;
    private List<DayTime> items;

    public DayTimeAdapter(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public DayTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(context).inflate(R.layout.day_view, parent, false);
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
//                holder.textView.setBackgroundColor(mCurrentBackgroundColor);"onListItemClickListener can't be null!"
//                holder.textView.setTextColor(mCurrentTextColor);
            holder.textView.setEnabled(true);
            holder.textView.setClickable(true);
        }

        if (null != onStyleChangeListener) {
            onStyleChangeListener.onStyleChange(holder, dayTime);
        }

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onListItemClickListener) {
                    onListItemClickListener.onListItemClick(v, dayTime);
                } else {
                    throw new IllegalViewArgumentException(ITEM_SELECTED_LISTENER_NOT_NULL_MESSAGE);
                }
            }
        });

        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boolean result = (null != onListItemLongClickListener);

                if (result) {
                    onListItemLongClickListener.onListItemLongClick(v, dayTime);
                } else {
                    throw new IllegalViewArgumentException(ITEM_LONG_SELECTED_LISTENER_NOT_NULL_MESSAGE);
                }

                return result;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != items) ? items.size() : 0;
    }

    public void setItems(List<DayTime> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<DayTime> getItems() {
        return items;
    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    public void setOnListItemLongClickListener(OnListItemLongClickListener onListItemLongClickListener) {
        this.onListItemLongClickListener = onListItemLongClickListener;
    }

    public void setOnStyleChangeListener(OnStyleChangeListener onStyleChangeListener) {
        this.onStyleChangeListener = onStyleChangeListener;
    }

    public class DayTimeViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public DayTimeViewHolder(@NonNull View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.day_view);
        }
    }

    /**
     * @author jonatan.salas
     */
    public interface OnListItemClickListener {

        void onListItemClick(@NonNull View view, @NonNull DayTime dayTime);
    }

    /**
     * @author jonatan.salas
     */
    public interface OnListItemLongClickListener {

        void onListItemLongClick(@NonNull View view, @NonNull DayTime dayTime);
    }

    public interface OnStyleChangeListener {

        void onStyleChange(@NonNull DayTimeAdapter.DayTimeViewHolder holder, @NonNull DayTime dayTime);
    }
}