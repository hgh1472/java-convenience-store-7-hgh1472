package store.exception;

public class NoPromotionException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 제품의 프로모션이 존재하지 않습니다.";

    public NoPromotionException() {
        super(MESSAGE);
    }
}
