package store.dto;

public class RemoveNonPromotionRequest {
    private final String productName;
    private final int quantity;

    public RemoveNonPromotionRequest(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}
