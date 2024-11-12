package store.repository;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.data.ProductInput;
import store.dto.data.PromotionInput;
import store.exception.DuplicatePromotionException;
import store.model.Product;
import store.model.Promotion;

public class ProductRepositoryTest {
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    @DisplayName("동일 상품에 여러 프로모션이 적용되지 않는다.")
    public void duplicatePromotion() {
        // GIVEN
        ProductInput productInput = new ProductInput(new String[]{"콜라", "1000", "10", "프로모션"});
        Promotion promotion = new Promotion(
                new PromotionInput(new String[]{"프로모션", "2", "1", "2023-01-01", "2023-12-31"}));
        productRepository.save(Product.of(productInput, Optional.of(promotion)));

        // WHEN - THEN
        Assertions.assertThatThrownBy(() -> productRepository.save(Product.of(productInput, Optional.of((promotion)))))
                .isInstanceOf(
                        DuplicatePromotionException.class);
    }
}
