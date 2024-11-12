package store.exception;

public class DuplicatePromotionException extends RuntimeException {

    public DuplicatePromotionException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
    }
}
