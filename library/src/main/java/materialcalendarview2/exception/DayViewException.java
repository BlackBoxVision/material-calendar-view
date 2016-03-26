package materialcalendarview2.exception;

/**
 * @author jonatan.salas
 */
public class DayViewException extends Exception {

    public DayViewException() {
        super();
    }

    public DayViewException(String detailMessage) {
        super(detailMessage);
    }

    public DayViewException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DayViewException(Throwable throwable) {
        super(throwable);
    }
}
