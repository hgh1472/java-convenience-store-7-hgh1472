package store.model;

import store.dto.ProductInput;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotionName;

    private Product(String name, int price, int quantity, String promotionName) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName;
    }

    public static Product from(ProductInput productInput) {
        return new Product(
                productInput.getName(),
                productInput.getPrice(),
                productInput.getQuantity(),
                productInput.getPromotionInput()
        );
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotionName() {
        return promotionName;
    }
}
