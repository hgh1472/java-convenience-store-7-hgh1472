package store.service;

import java.util.ArrayList;
import java.util.List;
import store.dto.OrderRequest;
import store.dto.ProductInput;
import store.dto.ProductShowResponse;
import store.dto.PromotionInput;
import store.model.Order;
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

    public List<Order> createOrders(List<OrderRequest> orderRequests) {
        List<Order> orders = new ArrayList<>();
        for (OrderRequest orderRequest : orderRequests) {
            validateOrderRequest(orderRequest);
            orders.add(Order.from(orderRequest));
        }
        return orders;
    }

    private void validateOrderRequest(OrderRequest orderRequest) {
        String productName = orderRequest.getProductName();
        List<Product> finds = productRepository.findByName(productName);
        validateProductExists(finds);
        validateQuantity(orderRequest, finds);
    }

    private void validateProductExists(List<Product> finds) {
        if (finds.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요");
        }
    }

    private void validateQuantity(OrderRequest orderRequest, List<Product> finds) {
        int productQuantity = finds.stream().mapToInt(Product::getQuantity).sum();
        if (productQuantity < orderRequest.getQuantity()) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }
}
