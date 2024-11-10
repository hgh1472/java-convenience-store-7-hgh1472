package store.model;

import java.time.LocalDate;
import store.dto.OrderRequest;

public class Order {
    private Product product;
    private int totalQuantity;
    private int promotionQuantity;
    private LocalDate orderDate;
    private Promotion promotion;

    private Order(Product product, int totalQuantity, LocalDate orderDate) {
        this.product = product;
        this.totalQuantity = totalQuantity;
        this.promotionQuantity = 0;
        this.orderDate = orderDate;
    }

    public Product getProduct() {
        return product;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public static Order of(Product product, OrderRequest orderRequest) {
        return new Order(product, orderRequest.getQuantity(), orderRequest.getOrderDate());
    }

    public void applyPromotion(Promotion promotion, int promotionQuantity) {
        this.promotion = promotion;
        this.promotionQuantity += promotionQuantity;
    }

    public void getAdditionalPromotion(int additionalQuantity) {
        this.totalQuantity += additionalQuantity;
        this.promotionQuantity = totalQuantity;
    }

    public void removeNonPromotionQuantity() {
        this.totalQuantity = promotionQuantity;
    }

    public boolean isPromotionApplied() {
        return promotionQuantity > 0;
    }
}
