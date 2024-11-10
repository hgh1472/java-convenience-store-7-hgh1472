package store.model;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private List<Order> orders;

    private int totalPrice;
    private int promotionDiscount;
    private int membershipDiscount;

    public Receipt() {
        this.orders = new ArrayList<Order>();
        this.totalPrice = 0;
        this.promotionDiscount = 0;
        this.membershipDiscount = 0;
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

    public void applyMembershipDiscount() {
        int canMembershipDiscount = totalPrice - promotionDiscount;
        this.membershipDiscount = Math.min(8000, canMembershipDiscount * 30 / 100);
    }
}
