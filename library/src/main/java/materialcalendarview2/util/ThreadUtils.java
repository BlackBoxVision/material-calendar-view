package materialcalendarview2.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import materialcalendarview2.data.callback.base.CallBack;

/**
 * @author jonatan.salas
 */
public final class ThreadUtils {
    private static final String LOG_TAG = ThreadUtils.class.getSimpleName();

    private ThreadUtils() { }

    @Nullable
    public static <T> T runInBackground(@NonNull final CallBack<T> callBack) {
        final Future<T> future = Executors.newSingleThreadExecutor().submit(getCallable(callBack));
        T object = null;

        try {
            object = future.get();
        } catch (ExecutionException | InterruptedException ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex.getCause());
        }

        return object;
    }

    @NonNull
    private static <T> Callable<T> getCallable(@NonNull final CallBack<T> callBack) {
        return () -> callBack.execute();
    }
}
