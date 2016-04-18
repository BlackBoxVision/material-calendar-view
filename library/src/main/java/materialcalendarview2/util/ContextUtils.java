package materialcalendarview2.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

/**
 * @author jonatan.salas
 */
public final class ContextUtils {

    //This context should be the ApplicationContext in order to prevent memory leaks..
    //Make sure of calling this in Application.onCreate() method.
    private static Context ctx = null;

    //Prevent instantiation..
    private ContextUtils() { }

    public static void init(Context context) {
        if (null == context) {
            throw new IllegalArgumentException("Context shouldn't be null");
        }

        ctx = context;
    }

    public static String getString(@StringRes int id) {
        checkNotNull();
        return ctx.getString(id);
    }

    public static  Drawable getDrawable(@DrawableRes int id) {
        checkNotNull();
        return ContextCompat.getDrawable(ctx, id);
    }

    public static int getColor(@ColorRes int id) {
        checkNotNull();
        return ContextCompat.getColor(ctx, id);
    }

    public static TypedArray getStylesAttributes(AttributeSet attrs, @StyleableRes int[] id) {
        checkNotNull();
        return ctx.obtainStyledAttributes(attrs, id);
    }

    public static Context getApplicationContext() {
        checkNotNull();
        return ctx.getApplicationContext();
    }

    static void checkNotNull() {
        if (null == ctx) {
            throw new IllegalArgumentException(
                    "ctx is null, please call init() method " +
                    "in onCreate from Application class"
            );
        }
    }
}
