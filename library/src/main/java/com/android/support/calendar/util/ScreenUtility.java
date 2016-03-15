package com.android.support.calendar.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author jonatan.salas
 */
public final class ScreenUtility {

    private ScreenUtility() { }

    public static int getScreenHeight(@NonNull Context context) {
        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }
}
