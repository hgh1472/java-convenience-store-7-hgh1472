package store.dto;

import store.model.Product;

public class ProductShowResponse {
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
        return new ProductShowResponse(product.getName(),
                product.getPrice(),
                product.getDefaultQuantity(),
                product.getPromotionQuantity(),
                product.getPromotionName().replace("null", ""));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (promotionQuantity != 0) {
            sb.append(String.format("- %s %,d원 %d개 %s\n", name, price, promotionQuantity, promotionName));
        }
        if (quantity == 0) {
            sb.append(String.format("- %s %,d원 재고 없음\n", name, price));
        }
        if (quantity != 0) {
            sb.append(String.format("- %s %,d원 %d개\n", name, price, quantity));
        }
        return sb.toString();
    }
}
