package com.jonatansalas.materialcalendarview.util;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author jonatan.salas
 */
public final class ThreadUtility {
    private static final String LOG_TAG = ThreadUtility.class.getSimpleName();

    private ThreadUtility() { }

    public static <T> T runInBackground(final CallBack<T> callBack) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Callable<T> callable = new Callable<T>() {
            @Override
            public T call() {
                return callBack.execute();
            }
        };

        final Future<T> future = executor.submit(callable);
        executor.shutdown();

        T object = null;

        try {
            object = future.get();
        } catch (ExecutionException | InterruptedException ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex.getCause());
        }

        return object;
    }

    public interface CallBack<T> {
        T execute();
    }
}
