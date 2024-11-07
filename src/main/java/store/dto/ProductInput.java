package store.dto;

import store.exception.InvalidProductException;

public class ProductInput {
    private String name;
    private int price;
    private int quantity;
    private String promotionInput;

    public ProductInput(String[] input) {
        if (input.length != 4) {
            throw new InvalidProductException();
        }
        this.name = input[0];
        this.price = Integer.parseInt(input[1]);
        this.quantity = Integer.parseInt(input[2]);
        this.promotionInput = input[3];
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

    public String getPromotionInput() {
        return promotionInput;
    }
}
