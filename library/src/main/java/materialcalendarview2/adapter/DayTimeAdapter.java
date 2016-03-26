package materialcalendarview2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import materialcalendarview2.R;
import materialcalendarview2.exception.IllegalViewArgumentException;
import materialcalendarview2.model.DayTime;
import materialcalendarview2.widget.DayView;

import java.util.List;

import static materialcalendarview2.exception.IllegalViewArgumentException.*;

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

        holder.dayView.setClickable(true);
        holder.dayView.setText(String.valueOf(dayTime.getDay()));
//            holder.dayView.setTextSize(mAdapterViewFontSize);
//            holder.dayView.setTextColor(mAdapterViewTextColor);

        if (!dayTime.isCurrentMonth()) {
            holder.dayView.setTypeface(Typeface.DEFAULT_BOLD);
//                holder.dayView.setBackgroundColor(mDisabledBackgroundColor);
//                holder.dayView.setTextColor(mDisabledTextColor);
            holder.dayView.setEnabled(false);
            holder.dayView.setClickable(false);
        }

        if (dayTime.isWeekend() && dayTime.isCurrentMonth()) {
//                holder.mDayView.setBackgroundColor(mWeekendBackgroundColor);
//                holder.dayView.setTextColor(mWeekendTextColor);
            holder.dayView.setTypeface(Typeface.DEFAULT_BOLD);
            holder.dayView.setEnabled(true);
            holder.dayView.setClickable(true);
        }

        if (dayTime.isCurrentDay() && dayTime.isCurrentMonth()) {
//                holder.dayView.setBackgroundColor(mCurrentBackgroundColor);"onListItemClickListener can't be null!"
//                holder.dayView.setTextColor(mCurrentTextColor);
            holder.dayView.setEnabled(true);
            holder.dayView.setClickable(true);
        }

        if (null != onStyleChangeListener) {
            onStyleChangeListener.onStyleChange(holder.dayView, dayTime);
        }

        holder.dayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onListItemClickListener) {
                    onListItemClickListener.onListItemClick(v, dayTime);
                } else {
                    throw new IllegalViewArgumentException(ITEM_SELECTED_LISTENER_NOT_NULL_MESSAGE);
                }
            }
        });

        holder.dayView.setOnLongClickListener(new View.OnLongClickListener() {
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
        public final DayView dayView;

        public DayTimeViewHolder(@NonNull View view) {
            super(view);
            this.dayView = (DayView) view.findViewById(R.id.day_view);
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

        void onStyleChange(@NonNull DayView dayView, @NonNull DayTime dayTime);
    }
}