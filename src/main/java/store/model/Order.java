package store.model;

import java.time.LocalDate;
import store.dto.OrderRequest;

public class Order {
    private String productName;
    private int totalQuantity;
    private int promotionQuantity;
    private LocalDate orderDate;
    private Promotion promotion;

    private Order(String productName, int totalQuantity, LocalDate orderDate) {
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.promotionQuantity = 0;
        this.orderDate = orderDate;
    }

    public String getProductName() {
        return productName;
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

    public static Order from(OrderRequest orderRequest) {
        return new Order(orderRequest.getProductName(), orderRequest.getQuantity(), orderRequest.getOrderDate());
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
}
