package store.model;

import java.util.List;
import store.dto.ProductInput;
import store.dto.PromotionInput;

public class Convenience {
    private List<Product> products;
    private List<Promotion> promotions;

    public Convenience(List<Product> products, List<Promotion> promotions) {
        this.products = products;
        this.promotions = promotions;
    }

    public void registerPromotions(List<PromotionInput> promotionInputs) {
        for (PromotionInput promotionInput : promotionInputs) {
            addPromotion(promotionInput);
        }
    }

    private void addPromotion(PromotionInput promotionInput) {
        Promotion promotion = new Promotion(promotionInput);
        promotions.add(promotion);
    }

    public void registerProducts(List<ProductInput> productInputs) {
        for (ProductInput productInput : productInputs) {
            addProduct(productInput);
        }
    }

    private void addProduct(ProductInput productInput) {
        Product product = Product.from(productInput);
        products.add(product);
    }

    public List<Product> showData() {
        return products;
    }
}
