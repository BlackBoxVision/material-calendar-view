package com.samsistemas.calendarview.constant;

/**
 * @author jonatan.salas
 */
public interface ScrollableConst {

    /**
     * Indicates that the CalendarView is in an idle, settled state. The current page
     * is fully in view and no animation is in progress.
     */
    int SCROLL_STATE_IDLE = 0;

    /**
     * Indicates that the CalendarView is currently being dragged by the user.
     */
    int SCROLL_STATE_DRAGGING = 1;

    /**
     * Indicates that the CalendarView is in the process of settling to a final position.
     */
    int SCROLL_STATE_SETTLING = 2;
}
