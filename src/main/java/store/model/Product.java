package store.model;

import java.util.Optional;
import store.dto.data.ProductInput;
import store.exception.DuplicatePromotionException;
import store.exception.ExceptionStatus;

public class Product {
    private String name;
    private int price;
    private int defaultQuantity;
    private int promotionQuantity;
    private Optional<Promotion> promotion;

    private Product(String name, int price, int quantity, int promotionQuantity, Optional<Promotion> promotion) {
        this.name = name;
        this.price = price;
        this.defaultQuantity = quantity;
        this.promotionQuantity = promotionQuantity;
        this.promotion = promotion;
    }

    public static Product of(ProductInput productInput, Optional<Promotion> promotion) {
        if (promotion.isEmpty()) {
            return new Product(
                    productInput.getName(), productInput.getPrice(), productInput.getQuantity(), 0, promotion);
        }
        return new Product(
                productInput.getName(), productInput.getPrice(), 0, productInput.getQuantity(), promotion);
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

    public Optional<Promotion> getPromotion() {
        return promotion;
    }

    public void sold(int quantity) {
        if (promotionQuantity >= quantity) {
            promotionQuantity -= quantity;
            return;
        }
        defaultQuantity -= quantity - promotionQuantity;
        promotionQuantity = 0;
    }

    public void registerProduct(Product product) {
        if (product.getPromotion().isEmpty()) {
            registerDefaultProduct(product);
            return;
        }
        if (this.promotion.isPresent()) {
            throw new DuplicatePromotionException(ExceptionStatus.DUPLICATE_PROMOTION);
        }
        registerPromotionProduct(product);
    }

    private void registerDefaultProduct(Product product) {
        this.defaultQuantity = product.getDefaultQuantity();
    }

    private void registerPromotionProduct(Product product) {
        this.promotionQuantity = product.getPromotionQuantity();
        this.promotion = product.getPromotion();
    }
}
