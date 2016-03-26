package materialcalendarview2.exception;

/**
 * @author jonatan.salas
 */
public class HeaderViewException extends Exception {

    public HeaderViewException() {
        super();
    }

    public HeaderViewException(String detailMessage) {
        super(detailMessage);
    }

    public HeaderViewException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public HeaderViewException(Throwable throwable) {
        super(throwable);
    }
}
