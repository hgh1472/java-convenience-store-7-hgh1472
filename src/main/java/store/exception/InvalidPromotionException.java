package store.exception;

public class InvalidPromotionException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 유효하지 않은 프로모션이 존재합니다.";

    public InvalidPromotionException() {
        super(MESSAGE);
    }
}
