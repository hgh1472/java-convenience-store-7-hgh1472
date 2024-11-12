package store.exception;

public class NoProductException extends RuntimeException {

    public NoProductException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
    }
}
