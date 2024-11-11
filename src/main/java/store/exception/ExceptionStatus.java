package store.exception;

public enum ExceptionStatus {
    INVALID_PROMOTION_DATA("잘못된 프로모션 데이터가 존재합니다."),
    DUPLICATE_PROMOTION("한 제품에 중복된 프로모션이 존재합니다."),
    INVALID_PRODUCT_DATA("잘못된 상품 데이터가 존재합니다."),

    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),
    NO_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OVER_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),

    NON_PROMOTION_PRODUCT("프로모션이 제공되지 않는 상품입니다."),
    NO_PROMOTION("존재하지 않는 프로모션입니다."),

    INVALID_YES_OR_NO("Y 또는 N을 입력해 주세요.");

    private static final String PREFIX = "[ERROR] ";

    private final String message;

    ExceptionStatus(String message) {
        this.message = PREFIX + message;
    }

    public String getMessage() {
        return message;
    }
}
