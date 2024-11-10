package store.dto;

import store.model.Product;

public class ProductShowResponse {
    private static final String NONE = "";
    private String name;
    private int price;
    private int quantity;
    private int promotionQuantity;
    private String promotionName;

    private ProductShowResponse(String name, int price, int quantity, int promotionQuantity, String promotionName) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionQuantity = promotionQuantity;
        this.promotionName = promotionName;
    }

    public static ProductShowResponse from(Product product) {
        String promotionName = NONE;
        if (product.getPromotion().isPresent()) {
            promotionName = product.getPromotion().get().getName();
        }
        return new ProductShowResponse(product.getName(),
                product.getPrice(),
                product.getDefaultQuantity(),
                product.getPromotionQuantity(),
                promotionName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        exchangePromotionString(sb);
        exchangeDefaultString(sb);
        return sb.toString();
    }

    private void exchangeDefaultString(StringBuilder sb) {
        if (quantity == 0) {
            sb.append(String.format("- %s %,d원 재고 없음\n", name, price));
        }
        if (quantity != 0) {
            sb.append(String.format("- %s %,d원 %d개\n", name, price, quantity));
        }
    }

    private void exchangePromotionString(StringBuilder sb) {
        if (!promotionName.equals(NONE)) {
            if (promotionQuantity != 0) {
                sb.append(String.format("- %s %,d원 %d개 %s\n", name, price, promotionQuantity, promotionName));
            }
            if (promotionQuantity == 0) {
                sb.append(String.format("- %s %,d원 재고 없음 %s\n", name, price, promotionName));
            }
        }
    }
}
