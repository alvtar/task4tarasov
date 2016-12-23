package exception;

public class PersistentException extends Exception {
    private static final long serialVersionUID = 1L;

    public PersistentException() {
    }

    public PersistentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistentException(String message) {
        super(message);
    }

    public PersistentException(Throwable cause) {
        super(cause);
    }
}
