package store.model;

import store.dto.ProductInput;

public class Product {
    private String name;
    private int price;
    private int defaultQuantity;
    private int promotionQuantity;
    private String promotionName;

    private Product(String name, int price, int quantity, int promotionQuantity, String promotionName) {
        this.name = name;
        this.price = price;
        this.defaultQuantity = quantity;
        this.promotionQuantity = promotionQuantity;
        this.promotionName = promotionName;
    }

    public static Product from(ProductInput productInput) {
        if (productInput.getPromotionInput().equals("null")) {
            return new Product(
                    productInput.getName(),
                    productInput.getPrice(),
                    productInput.getQuantity(),
                    0,
                    productInput.getPromotionInput()
            );
        }
        return new Product(
                productInput.getName(),
                productInput.getPrice(),
                0,
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

    public int getDefaultQuantity() {
        return defaultQuantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void soldDefault(int quantity) {
        this.defaultQuantity -= quantity;
    }

    public void soldPromotion(int quantity) {
        this.promotionQuantity -= quantity;
    }

    public void registerDefaultProduct(int quantity) {
        this.defaultQuantity = quantity;
    }

    public void registerPromotionProduct(int promotionQuantity, String promotionName) {
        this.promotionQuantity = promotionQuantity;
    }
}
