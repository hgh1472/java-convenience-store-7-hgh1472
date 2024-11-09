package store.model;

import store.dto.OrderRequest;

public class Order {
    private String productName;
    private int quantity;

    private Order(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public static Order from(OrderRequest orderRequest) {
        return new Order(orderRequest.getProductName(), orderRequest.getQuantity());
    }
}
