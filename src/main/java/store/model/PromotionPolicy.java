package store.model;

public enum PromotionPolicy {
    NONE(0, 0),
    BUY_ONE_GET_ONE(1,1),
    BUY_TWO_GET_ONE(2, 1);

    private final int buy;
    private final int get;

    PromotionPolicy(int buy, int get) {
        this.buy = buy;
        this.get = get;
    }

    public static PromotionPolicy of(int buy, int get) {
        for (PromotionPolicy p : PromotionPolicy.values()) {
            if (p.buy == buy && p.get == get) {
                return p;
            }
        }
        return NONE;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public int getSetQuantity() {
        return this.buy + this.get;
    }

    public int getPromotionQuantityInOrderQuantity(int orderQuantity) {
        int appliedPromotionCount = orderQuantity / (buy + get);
        return get * appliedPromotionCount;
    }
}
