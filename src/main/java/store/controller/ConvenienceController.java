package store.controller;

import java.util.List;
import store.dto.AdditionalQuantityRequest;
import store.dto.OrderRequest;
import store.dto.ProductInput;
import store.dto.PromotionInput;
import store.dto.RemoveNonPromotionRequest;
import store.exception.InvalidPromotionDateException;
import store.exception.NoPromotionException;
import store.model.Order;
import store.model.Product;
import store.model.Promotion;
import store.model.Receipt;
import store.service.ConvenienceService;
import store.view.InputView;
import store.view.OutputView;
import store.view.Reader;

public class ConvenienceController {
    private final Reader reader;
    private final ConvenienceService service;
    private final OutputView outputView;
    private final InputView inputView;

    public ConvenienceController(Reader reader, ConvenienceService convenienceService, OutputView outputView,
                                 InputView inputView) {
        this.reader = reader;
        this.service = convenienceService;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        registerData();
        while (true) {
            printData();
            List<Order> orders = getOrders();
            for (Order order : orders) {
                applyPromotions(order);
            }
            Boolean isMembership = retryIfHasError(inputView::readMembership);
            Receipt receipt = service.getReceipt(orders, isMembership);
            outputView.showReceipt(receipt);
            Boolean isContinue = retryIfHasError(inputView::isContinuePurchase);
            if (!isContinue) {
                break;
            }
        }
    }

    private void applyPromotions(Order order) {
        try {
            Product promotionProduct = service.getPromotionProduct(order);
            Promotion promotion = service.getPromotion(promotionProduct);
            promotion.validatePromotionDate(order.getOrderDate());
            service.applyPromotion(promotionProduct.getDefaultQuantity(), order, promotion);
            int additional = service.getAdditionalPromotionQuantity(promotionProduct, order, promotion.getPolicy());
            readAdditionalOrPurchase(order, additional, promotionProduct);
            service.commitOrder(order);
        } catch (NoPromotionException | InvalidPromotionDateException ignored) {
        }
    }

    private void readAdditionalOrPurchase(Order order, int additional, Product promotionProduct) {
        if (additional > 0) {
            if (retryIfHasError(() -> inputView.readAdditionalQuantity(
                    new AdditionalQuantityRequest(promotionProduct.getName(), additional)))) {
                order.getAdditionalPromotion(additional);
            }
        }
        if (additional < 0) {
            if (!retryIfHasError(() -> inputView.readContinuePurchase(
                    new RemoveNonPromotionRequest(promotionProduct.getName(),
                            -additional)))) {
                order.removeNonPromotionQuantity();
            }
        }
    }

    private List<Order> getOrders() {
        return retryIfHasError(() -> {
            List<OrderRequest> orderRequests = inputView.readOrders();
            return service.createOrders(orderRequests);
        });
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
        outputView.printProducts(service.showProducts());
    }

    private void registerData() {
        readPromotionsFromResources();
        readProductsFromResources();
    }

    private void readPromotionsFromResources() {
        List<PromotionInput> promotionInputs = reader.readPromotions();
        service.registerPromotions(promotionInputs);
    }

    private void readProductsFromResources() {
        List<ProductInput> productInputs = reader.readProducts();
        service.registerProducts(productInputs);
    }
}
