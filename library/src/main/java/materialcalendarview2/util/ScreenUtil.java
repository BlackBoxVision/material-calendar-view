package materialcalendarview2.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author jonatan.salas
 */
public final class ScreenUtil {

    private ScreenUtil() { }

    public static int getScreenHeight(@NonNull Context context) {
        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }
}
