package store.exception;

public class InvalidPromotionException extends RuntimeException {

    public InvalidPromotionException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
    }
}
