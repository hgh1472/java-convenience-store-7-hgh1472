package store.service;

import java.util.ArrayList;
import java.util.List;
import store.dto.OrderRequest;
import store.dto.ProductInput;
import store.dto.ProductShowResponse;
import store.dto.PromotionInput;
import store.exception.NoProductException;
import store.exception.NoPromotionException;
import store.model.Order;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionPolicy;
import store.model.Receipt;
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
            try {
                validateOrderRequest(orderRequest);
                int price = productRepository.findByName(orderRequest.getProductName()).getPrice();
                orders.add(Order.of(orderRequest, price));
            } catch (NoProductException e) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
        }
        return orders;
    }

    private void validateOrderRequest(OrderRequest orderRequest) {
        String productName = orderRequest.getProductName();
        Product product = productRepository.findByName(productName);
        validateQuantity(orderRequest, product);
    }

    private void validateQuantity(OrderRequest orderRequest, Product product) {
        int productQuantity = product.getDefaultQuantity() + product.getPromotionQuantity();
        if (productQuantity < orderRequest.getQuantity()) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public Promotion getPromotion(Product promotionProduct) {
        return promotionRepository.findByPromotionName(promotionProduct.getPromotionName());
    }

    public Product getPromotionProduct(Order order) {
        Product product = productRepository.findByName(order.getProductName());
        if (product.getPromotionName().equals("null")) {
            throw new NoPromotionException();
        }
        return product;
    }

    public void applyPromotion(int promotionQuantity, Order order, Promotion promotion) {
        PromotionPolicy policy = promotion.getPolicy();

        int promotionSetQuantity = policy.getGet() + policy.getBuy();
        int needPromotionQuantity = (order.getTotalQuantity() / promotionSetQuantity) * promotionSetQuantity;
        int canPromotionQuantity = 0;
        while (canPromotionQuantity + promotionSetQuantity <= needPromotionQuantity
                && canPromotionQuantity + promotionSetQuantity <= promotionQuantity) {
            canPromotionQuantity += promotionSetQuantity;
        }
        order.applyPromotion(promotion, canPromotionQuantity);
    }

    public int getAdditionalPromotionQuantity(Product promotionProduct, Order order, PromotionPolicy policy) {
        int notAppliedPromotionQuantity = order.getTotalQuantity() - order.getPromotionQuantity();
        if (notAppliedPromotionQuantity == 0) {
            return 0;
        }
        if (notAppliedPromotionQuantity == policy.getBuy() &&
                promotionProduct.getDefaultQuantity()
                        >= policy.getGet() + policy.getBuy() + order.getPromotionQuantity()) {
            return policy.getGet();
        }
        return -notAppliedPromotionQuantity;
    }

    public void commitOrder(Order order) {
        int defaultQuantity = order.getTotalQuantity() - order.getPromotionQuantity();

        Product product = productRepository.findByName(order.getProductName());
        product.soldDefault(defaultQuantity);
        if (order.getPromotionQuantity() > 0) {
            product.soldPromotion(order.getPromotionQuantity());
        }
    }

    public Receipt getReceipt(List<Order> orders, boolean isMembership) {
        Receipt receipt = new Receipt();
        for (Order order : orders) {
            Product product = productRepository.findByName(order.getProductName());
            int totalPrice = product.getPrice() * order.getTotalQuantity();
            int promotionDiscount = 0;
            if (order.getPromotionQuantity() > 0) {
                PromotionPolicy policy = promotionRepository.findByPromotionName(product.getPromotionName())
                        .getPolicy();
                int freeCount = order.getPromotionQuantity() / (policy.getBuy() + policy.getGet());
                promotionDiscount += freeCount * product.getPrice();
            }
            receipt.addOrder(order, totalPrice, promotionDiscount);
        }
        if (isMembership) {
            receipt.applyMembershipDiscount();
        }
        return receipt;
    }
}
