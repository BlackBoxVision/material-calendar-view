package materialcalendarview2.exception;

/**
 * @author jonatan.salas
 */
public class MaterialCalendarViewException extends Exception {

    public MaterialCalendarViewException() {
        super();
    }

    public MaterialCalendarViewException(String detailMessage) {
        super(detailMessage);
    }

    public MaterialCalendarViewException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public MaterialCalendarViewException(Throwable throwable) {
        super(throwable);
    }
}
