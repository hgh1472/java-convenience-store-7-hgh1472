package store.model;

import java.time.LocalDate;
import store.dto.OrderRequest;

public class Order {
    private String productName;
    private int price;
    private int totalQuantity;
    private int promotionQuantity;
    private LocalDate orderDate;
    private Promotion promotion;

    private Order(String productName, int price, int totalQuantity, LocalDate orderDate) {
        this.productName = productName;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.promotionQuantity = 0;
        this.orderDate = orderDate;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
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

    public static Order of(OrderRequest orderRequest, int price) {
        return new Order(orderRequest.getProductName(), price, orderRequest.getQuantity(), orderRequest.getOrderDate());
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
