package store.controller;

import java.util.List;
import store.dto.OrderRequest;
import store.dto.ProductInput;
import store.dto.PromotionInput;
import store.model.Order;
import store.service.ConvenienceService;
import store.view.InputView;
import store.view.OutputView;
import store.view.Reader;

public class ConvenienceController {
    private final Reader reader;
    private final ConvenienceService convenienceService;
    private final OutputView outputView;
    private final InputView inputView;

    public ConvenienceController(Reader reader, ConvenienceService convenienceService, OutputView outputView,
                                 InputView inputView) {
        this.reader = reader;
        this.convenienceService = convenienceService;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        registerData();
        printData();
        List<Order> orders = getOrders();
    }

    private List<Order> getOrders() {
        List<OrderRequest> orderRequests = retryIfHasError(inputView::readOrders);
        return convenienceService.createOrders(orderRequests);
    }

    private <T> T retryIfHasError(Retryable<T> retryable) {
        while (true) {
            try {
                return retryable.execute();
            } catch (IllegalArgumentException e) {
                outputView.showError(e);
            }
        }
    }

    private void printData() {
        outputView.printProducts(convenienceService.showProducts());
    }

    private void registerData() {
        readPromotionsFromResources();
        readProductsFromResources();
    }

    private void readPromotionsFromResources() {
        List<PromotionInput> promotionInputs = reader.readPromotions();
        convenienceService.registerPromotions(promotionInputs);
    }

    private void readProductsFromResources() {
        List<ProductInput> productInputs = reader.readProducts();
        convenienceService.registerProducts(productInputs);
    }
}
