package store.dto;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderRequest {
    private static final String REQUEST_PATTERN = "\\[([a-zA-Z가-힣0-9]+)-(\\d+)\\]";
    private static final Pattern PATTERN = Pattern.compile(REQUEST_PATTERN);

    private final String productName;
    private final int quantity;
    private final LocalDate orderDate;

    public OrderRequest(String input) {
        Matcher matcher = PATTERN.matcher(input);
        validate(matcher);
        productName = matcher.group(1);
        quantity = parseQuantity(matcher.group(2));
        orderDate = DateTimes.now().toLocalDate();
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    private void validate(Matcher matcher) {
        validateForm(matcher);
    }

    private void validateForm(Matcher matcher) {
        if (!matcher.matches()) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

    private int parseQuantity(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 구매 수량이 잘못되었습니다.");
        }
    }
}
