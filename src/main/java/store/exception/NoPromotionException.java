package store.exception;

public class NoPromotionException extends RuntimeException {

    public NoPromotionException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
    }
}
