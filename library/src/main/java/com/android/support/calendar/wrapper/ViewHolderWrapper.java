package com.android.support.calendar.wrapper;

import android.widget.TextView;

import com.android.support.calendar.adapter.DayTimeAdapter;

/**
 * @author jonatan.salas
 */
public class ViewHolderWrapper {
    private DayTimeAdapter.DayTimeViewHolder holder;

    public ViewHolderWrapper(DayTimeAdapter.DayTimeViewHolder viewHolder) {
        this.holder = viewHolder;
    }

    public TextView getView() {
        return this.holder.textView;
    }
}
