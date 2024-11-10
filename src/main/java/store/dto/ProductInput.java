package store.dto;

import store.exception.ExceptionStatus;
import store.exception.InvalidProductException;

public class ProductInput {
    private static final String NON_PROMOTION = "null";

    private String name;
    private int price;
    private int quantity;
    private String promotionInput;

    public ProductInput(String[] input) {
        if (input.length != 4) {
            throw new InvalidProductException(ExceptionStatus.INVALID_PRODUCT_DATA);
        }
        this.name = input[0];
        this.price = parsePrice(input[1]);
        this.quantity = parseQuantity(input[2]);
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

    public boolean isAvailablePromotion() {
        return !promotionInput.equals(NON_PROMOTION);
    }

    private int parsePrice(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidProductException(ExceptionStatus.INVALID_PRODUCT_DATA);
        }
    }

    private int parseQuantity(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidProductException(ExceptionStatus.INVALID_PRODUCT_DATA);
        }
    }
}
