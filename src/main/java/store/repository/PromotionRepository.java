package store.repository;

import java.util.ArrayList;
import java.util.List;
import store.exception.ExceptionStatus;
import store.exception.NoPromotionException;
import store.model.Promotion;

public class PromotionRepository {
    private List<Promotion> promotions;

    public PromotionRepository() {
        this.promotions = new ArrayList<>();
    }

    public void save(Promotion promotion) {
        promotions.add(promotion);
    }

    public Promotion findByPromotionName(String promotionName) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionName))
                .findFirst()
                .orElseThrow(() -> new NoPromotionException(ExceptionStatus.NO_PROMOTION));
    }
}
