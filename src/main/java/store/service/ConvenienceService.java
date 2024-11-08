package store.service;

import java.util.List;
import store.dto.ProductInput;
import store.dto.ProductShowResponse;
import store.dto.PromotionInput;
import store.model.Product;
import store.model.Promotion;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

public class ConvenienceService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public ConvenienceService(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public void registerPromotions(List<PromotionInput> promotionInputs) {
        for (PromotionInput promotionInput : promotionInputs) {
            addPromotion(promotionInput);
        }
    }

    private void addPromotion(PromotionInput promotionInput) {
        Promotion promotion = new Promotion(promotionInput);
        promotionRepository.save(promotion);
    }

    public void registerProducts(List<ProductInput> productInputs) {
        for (ProductInput productInput : productInputs) {
            addProduct(productInput);
        }
    }

    private void addProduct(ProductInput productInput) {
        Product product = Product.from(productInput);
        productRepository.save(product);
    }

    public List<ProductShowResponse> showProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductShowResponse::from)
                .toList();
    }
}
