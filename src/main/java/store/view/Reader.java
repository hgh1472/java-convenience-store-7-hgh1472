package store.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.dto.data.ProductInput;
import store.dto.data.PromotionInput;
import store.exception.CustomIOException;

public class Reader {
    private static final String PRODUCT_PATH = "src/main/resources/products.md";
    private static final String PROMOTION_PATH = "src/main/resources/promotions.md";

    private static final String DELIMITER = ",";

    public List<PromotionInput> readPromotions() {
        List<PromotionInput> promotionInputs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PROMOTION_PATH))) {
            br.readLine();
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] input = line.split(DELIMITER);
                PromotionInput promotionInput = new PromotionInput(input);
                promotionInputs.add(promotionInput);
            }
        } catch (IOException e) {
            throw new CustomIOException(e.getMessage());
        }
        return promotionInputs;
    }

    public List<ProductInput> readProducts() {
        List<ProductInput> productInputs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_PATH))) {
            br.readLine();
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] input = line.split(DELIMITER);
                ProductInput productInput = new ProductInput(input);
                productInputs.add(productInput);
            }
        } catch (IOException e) {
            throw new CustomIOException(e.getMessage());
        }
        return productInputs;
    }
}
