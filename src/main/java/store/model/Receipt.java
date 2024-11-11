package store.model;

import java.util.List;
import store.exception.ExceptionStatus;
import store.exception.NoPromotionException;

public class Receipt {
    private List<Order> orders;

    private int totalPrice;
    private int promotionDiscount;
    private int membershipDiscount;

    private Receipt(List<Order> orders) {
        this.orders = orders;
        this.totalPrice = 0;
        this.totalPrice = orders.stream()
                .mapToInt(order -> order.getTotalQuantity() * order.getProduct().getPrice())
                .sum();
        this.promotionDiscount = 0;
        this.membershipDiscount = 0;
    }

    public static Receipt from(List<Order> orders) {
        return new Receipt(orders);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public void addOrder(Order order, int totalPrice, int promotionDiscount) {
        orders.add(order);
        this.totalPrice += totalPrice;
        this.promotionDiscount += promotionDiscount;
    }

    public void applyPromotionDiscount() {
        for (Order order : orders) {
            if (order.isPromotionApplied()) {
                Product product = order.getProduct();
                PromotionPolicy policy = product.getPromotion()
                        .orElseThrow(() -> new NoPromotionException(ExceptionStatus.NO_PROMOTION)).getPolicy();
                int freeCount = order.getPromotionQuantity() / policy.getSetQuantity();
                this.promotionDiscount += freeCount * product.getPrice();
            }
        }
    }

    public void applyMembershipDiscount() {
        int canMembershipDiscount = 0;
        for (Order order : orders) {
            if (!order.isPromotionApplied()) {
                canMembershipDiscount += order.getTotalQuantity() * order.getProduct().getPrice();
            }
        }
        this.membershipDiscount = Math.min(8000, canMembershipDiscount * 30 / 100);
    }
}
