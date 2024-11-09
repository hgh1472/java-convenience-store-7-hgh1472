package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.ProductInput;
import store.dto.PromotionInput;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionPolicy;
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
        String[] productInfo = {"콜라", "1000", "10", "탄산2+1"};
        List<ProductInput> productInputs = List.of(new ProductInput(productInfo));

        // WHEN
        convenienceService.registerProducts(productInputs);

        // THEN
        List<Product> finds = productRepository.findByName("콜라");
        assertThat(finds).hasSize(1);
        Product product = finds.getFirst();
        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getQuantity()).isEqualTo(10);
        assertThat(product.getPromotionName()).isEqualTo("탄산2+1");
    }
}
