package materialcalendarview2.exception;

/**
 * @author jonatan.salas
 */
public class AdapterViewException extends Exception {

    public AdapterViewException() {
        super();
    }

    public AdapterViewException(String detailMessage) {
        super(detailMessage);
    }

    public AdapterViewException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AdapterViewException(Throwable throwable) {
        super(throwable);
    }
}
