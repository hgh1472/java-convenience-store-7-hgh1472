package store.model;

import java.util.Objects;

public class PromotionPolicy {
    private final int buy;
    private final int get;

    private PromotionPolicy(int buy, int get) {
        this.buy = buy;
        this.get = get;
    }

    public static PromotionPolicy of(int buy, int get) {
        return new PromotionPolicy(buy, get);
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public int getPromotionSet(int orderQuantity) {
        return orderQuantity / (buy + get);
    }

    public int getPromotionQuantityInOrderQuantity(int orderQuantity) {
        int appliedPromotionCount = orderQuantity / (buy + get);
        return get * appliedPromotionCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PromotionPolicy that = (PromotionPolicy) o;
        return buy == that.buy && get == that.get;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buy, get);
    }
}
