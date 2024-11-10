package store.dto;

public class AdditionalQuantityRequest {
    private final String productName;
    private final int additionalQuantity;

    public AdditionalQuantityRequest(String productName, int additionalQuantity) {
        this.productName = productName;
        this.additionalQuantity = additionalQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getAdditionalQuantity() {
        return additionalQuantity;
    }
}
