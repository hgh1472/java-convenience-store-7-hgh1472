package store.model;

import java.time.LocalDate;
import store.dto.PromotionInput;
import store.exception.InvalidPromotionDateException;

public class Promotion {
    private final String name;
    private final PromotionPolicy policy;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(PromotionInput promotionInput) {
        this.name = promotionInput.getName();
        this.policy = PromotionPolicy.of(promotionInput.getBuy(), promotionInput.getGet());
        this.startDate = promotionInput.getStartDate();
        this.endDate = promotionInput.getEndDate();
    }

    public String getName() {
        return name;
    }

    public PromotionPolicy getPolicy() {
        return policy;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void validatePromotionDate(LocalDate date) {
        if (date.isBefore(startDate) || date.isAfter(endDate)) {
            throw new InvalidPromotionDateException();
        }
    }
}
