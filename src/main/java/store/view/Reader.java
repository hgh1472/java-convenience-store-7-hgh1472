package store.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import store.dto.data.ProductInput;
import store.dto.data.PromotionInput;
import store.exception.CustomIOException;

public class Reader {
    private static final String PRODUCT_PATH = "src/main/resources/products.md";
    private static final String PROMOTION_PATH = "src/main/resources/promotions.md";

    private static final String DELIMITER = ",";

    public List<PromotionInput> readPromotions() {
        try (BufferedReader br = new BufferedReader(new FileReader(PROMOTION_PATH))) {
            skipHeader(br);
            return readData(br, PromotionInput::new);
        } catch (IOException e) {
            throw new CustomIOException(e.getMessage());
        }
    }

    public List<ProductInput> readProducts() {
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_PATH))) {
            skipHeader(br);
            return readData(br, ProductInput::new);
        } catch (IOException e) {
            throw new CustomIOException(e.getMessage());
        }
    }

    private void skipHeader(BufferedReader br) throws IOException {
        br.readLine();
    }

    private <T> List<T> readData(BufferedReader br, Function<String[], T> mapper) throws IOException {
        List<T> data = new ArrayList<>();
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            data.add(mapper.apply(line.split(DELIMITER)));
        }
        return data;
    }
}
