package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.OrderRequest;
import store.dto.ProductInput;
import store.dto.PromotionInput;
import store.model.Order;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionPolicy;
import store.model.Receipt;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

public class ConvenienceServiceTest {

    private ConvenienceService convenienceService;
    private ProductRepository productRepository;
    private PromotionRepository promotionRepository;

    @BeforeEach
    public void setUp() {
        productRepository = new ProductRepository();
        promotionRepository = new PromotionRepository();
        convenienceService = new ConvenienceService(productRepository, promotionRepository);
    }

    @Test
    @DisplayName("프로모션을 등록한다.")
    public void registerPromotionTest() {
        // GIVEN
        String[] promotionInfo = {"탄산2+1", "2", "1", "2024-01-01", "2024-12-31"};
        List<PromotionInput> promotionInputs = List.of(new PromotionInput(promotionInfo));

        // WHEN
        convenienceService.registerPromotions(promotionInputs);

        // THEN
        Promotion find = promotionRepository.findByPromotionName("탄산2+1");
        assertThat(find.getName()).isEqualTo("탄산2+1");
        assertThat(find.getPolicy()).isEqualTo(PromotionPolicy.of(2, 1));
        assertThat(find.getStartDate()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(find.getEndDate()).isEqualTo(LocalDate.of(2024, 12, 31));
    }

    @Test
    @DisplayName("제품을 등록한다.")
    public void registerProduct() {
        // GIVEN
        String[] productInfo = {"콜라", "1000", "10", "null"};
        List<ProductInput> productInputs = List.of(new ProductInput(productInfo));

        // WHEN
        convenienceService.registerProducts(productInputs);

        // THEN
        Product product = productRepository.findByName("콜라");
        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getDefaultQuantity()).isEqualTo(10);
        assertThat(product.getPromotion()).isEmpty();
    }

    @Test
    @DisplayName("제품을 주문한다.")
    public void order() {
        // GIVEN
        String[] coke = {"콜라", "1000", "10", "탄산2+1"};
        String[] ramen = {"컵라면", "1700", "10", "null"};
        productRepository.save(Product.of(new ProductInput(coke), Optional.empty()));
        productRepository.save(Product.of(new ProductInput(ramen), Optional.empty()));

        List<OrderRequest> orderRequests = List.of(new OrderRequest("[컵라면-2]"), new OrderRequest("[콜라-4]"));

        // WHEN
        List<Order> orders = convenienceService.createOrders(orderRequests);

        // THEN
        assertThat(orders).hasSize(2);

        Order first = orders.getFirst();
        assertThat(first.getProduct().getName()).isEqualTo("컵라면");
        assertThat(first.getTotalQuantity()).isEqualTo(2);

        Order last = orders.getLast();
        assertThat(last.getProduct().getName()).isEqualTo("콜라");
        assertThat(last.getTotalQuantity()).isEqualTo(4);
    }

    @Test
    @DisplayName("프로모션 기간 내에 포함되지 않는 경우, 프로모션이 적용되지 않는다.")
    public void promotionDate() {
        // GIVEN
        String[] promotionInfo = {"프로모션", "2", "1", "2023-01-01", "2023-12-31"};
        Promotion promotion = new Promotion(new PromotionInput(promotionInfo));
        promotionRepository.save(promotion);

        String[] coke = {"콜라", "1000", "10", "프로모션"};
        productRepository.save(Product.of(new ProductInput(coke), Optional.of(promotion)));

        List<OrderRequest> orderRequests = List.of(new OrderRequest("[콜라-3]"));
        // WHEN
        List<Order> orders = convenienceService.createOrders(orderRequests);

        // THEN
        assertThat(orders).hasSize(1);
        assertThat(orders.getFirst().getPromotionQuantity()).isEqualTo(0);
    }

    @Test
    @DisplayName("프로모션 혜택을 적용한다.")
    public void promotion() {
        // GIVEN
        String[] promotionInfo = {"프로모션", "2", "1", "2024-01-01", "2024-12-31"};
        Promotion promotion = new Promotion(new PromotionInput(promotionInfo));
        promotionRepository.save(promotion);

        String[] coke = {"콜라", "1000", "10", "프로모션"};
        productRepository.save(Product.of(new ProductInput(coke), Optional.of(promotion)));

        Order order = Order.of(productRepository.findByName("콜라"), new OrderRequest("[콜라-3]"));
        // WHEN
        convenienceService.applyPromotion(order, promotion);

        // THEN
        assertThat(order.getPromotion()).isEqualTo(promotion);
        assertThat(order.getPromotionQuantity()).isEqualTo(3);
    }
}
