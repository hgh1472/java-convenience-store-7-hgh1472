package store.exception;

public class InvalidProductException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 유효하지 않은 제품이 존재합니다.";

    public InvalidProductException() {
        super(MESSAGE);
    }
}
