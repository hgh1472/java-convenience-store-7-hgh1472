package store.controller;

import java.util.List;
import store.dto.ProductInput;
import store.dto.ProductShowResponse;
import store.dto.PromotionInput;
import store.model.Convenience;
import store.model.Product;
import store.view.OutputView;
import store.view.Reader;

public class ConvenienceController {
    private final Reader reader;
    private final Convenience convenience;
    private final OutputView outputView;

    public ConvenienceController(Reader reader, Convenience convenience, OutputView outputView) {
        this.reader = reader;
        this.convenience = convenience;
        this.outputView = outputView;
    }

    public void run() {
        registerData();
        printData();
    }

    private void printData() {
        List<Product> products = convenience.showData();
        outputView.printProducts(products.stream().map(ProductShowResponse::from).toList());
    }

    private void registerData() {
        readPromotionsFromResources();
        readProductsFromResources();
    }

    private void readPromotionsFromResources() {
        List<PromotionInput> promotionInputs = reader.readPromotions();
        convenience.registerPromotions(promotionInputs);
    }

    private void readProductsFromResources() {
        List<ProductInput> productInputs = reader.readProducts();
        convenience.registerProducts(productInputs);
    }

    private void showData() {
        List<Product> products = convenience.showData();

    }
}
