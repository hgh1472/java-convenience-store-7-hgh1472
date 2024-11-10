package store.exception;

public class InvalidProductException extends RuntimeException {

    public InvalidProductException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
    }
}
