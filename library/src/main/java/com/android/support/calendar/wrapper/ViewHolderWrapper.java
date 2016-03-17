package com.android.support.calendar.wrapper;

import com.android.support.calendar.adapter.DayTimeAdapter;
import com.android.support.calendar.widget.DayView;

/**
 * @author jonatan.salas
 */
public class ViewHolderWrapper {
    private DayTimeAdapter.DayTimeViewHolder holder;

    public ViewHolderWrapper(DayTimeAdapter.DayTimeViewHolder viewHolder) {
        this.holder = viewHolder;
    }

    public DayView getView() {
        return this.holder.textView;
    }
}
