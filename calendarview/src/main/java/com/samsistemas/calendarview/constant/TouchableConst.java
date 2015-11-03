package com.samsistemas.calendarview.constant;

/**
 * @author jonatan.salas
 */
public interface TouchableConst {
    boolean USE_CACHE = false;
    int MIN_DISTANCE_FOR_FLING = 25; // dips
    int DEFAULT_GUTTER_SIZE = 16; // dips
    int MIN_FLING_VELOCITY = 400; // dips

    /**
     * Sentinel value for no current active pointer.
     */
    int INVALID_POINTER = -1;

    // If the CalendarView is at least this close to its final position, complete the scroll
    // on touch down and let the user interact with the content inside instead of
    // "catching" the flinging Calendar.
    int CLOSE_ENOUGH = 2; // dp
}
