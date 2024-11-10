package store.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import store.exception.ExceptionStatus;
import store.exception.InvalidPromotionException;

public class PromotionInput {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public PromotionInput(String[] input) {
        validate(input);
        this.name = input[0];
        this.buy = parseNumber(input[1]);
        this.get = parseNumber(input[2]);
        this.startDate = parseDate(input[3]);
        this.endDate = parseDate(input[4]);
    }

    public void validate(String[] input) {
        validateInputLength(input);
    }

    private void validateInputLength(String[] input) {
        if (input.length != 5) {
            throw new InvalidPromotionException(ExceptionStatus.INVALID_PROMOTION_DATA);
        }
    }

    private int parseNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidPromotionException(ExceptionStatus.INVALID_PROMOTION_DATA);
        }
    }

    private LocalDate parseDate(String input) {
        try {
            return LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new InvalidPromotionException(ExceptionStatus.INVALID_PROMOTION_DATA);
        }
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
