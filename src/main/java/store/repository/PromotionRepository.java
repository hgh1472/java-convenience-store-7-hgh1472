package store.repository;

import java.util.ArrayList;
import java.util.List;
import store.model.Promotion;

public class PromotionRepository {
    private List<Promotion> promotions;

    public PromotionRepository() {
        this.promotions = new ArrayList<>();
    }

    public void save(Promotion promotion) {
        promotions.add(promotion);
    }
}
