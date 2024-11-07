package store.dto;

import store.model.Product;

public class ProductShowResponse {
    private String name;
    private int price;
    private int quantity;
    private String promotionName;

    private ProductShowResponse(String name, int price, int quantity, String promotionName) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName;
    }

    public static ProductShowResponse from(Product product) {
        return new ProductShowResponse(product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getPromotionName().replace("null", ""));
    }

    @Override
    public String toString() {
        if (quantity == 0) {
            return String.format("- %s %,d원 재고 없음 %s", name, price, promotionName);
        }
        return String.format("- %s %,d원 %d개 %s", name, price, quantity, promotionName);
    }
}
