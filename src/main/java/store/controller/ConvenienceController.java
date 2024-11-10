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
            List<Order> orders = getOrders();
            Receipt receipt = service.getReceipt(orders);
            applyMembership(receipt);
            outputView.showReceipt(receipt);
            if (!retryIfHasError(inputView::isContinuePurchase)) {
                return;
            }
        }
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

    private List<Order> getOrders() {
        printData();
        List<Order> orders = retryIfHasError(() -> {
            List<OrderRequest> orderRequests = inputView.readOrders();
            return service.createOrders(orderRequests);
        });
        applyPromotions(orders);
        return orders;
    }

    private void printData() {
        outputView.printProducts(service.showProducts());
    }

    private void applyPromotions(List<Order> orders) {
        for (Order order : orders) {
            applyPromotion(order);
        }
    }

    private void applyPromotion(Order order) {
        try {
            Promotion promotion = order.getProduct().getPromotion().orElseThrow(NoPromotionException::new);
            promotion.validatePromotionDate(order.getOrderDate());
            service.applyPromotion(order, promotion);
            int additional = service.getAdditionalPromotionQuantity(order, promotion.getPolicy());
            readAdditionalOrPurchase(order, additional);
            service.commitOrder(order);
        } catch (NoPromotionException | InvalidPromotionDateException ignored) {
        }
    }

    private void readAdditionalOrPurchase(Order order, int additional) {
        Product product = order.getProduct();
        if (additional > 0) {
            decideAdditionalPromotion(order, additional);
        }
        if (additional < 0) {
            decideExcludeNonPromotion(order, additional);
        }
    }

    private void decideAdditionalPromotion(Order order, int additional) {
        Product promotionProduct = order.getProduct();
        if (retryIfHasError(() -> inputView.readAdditionalQuantity(
                new AdditionalQuantityRequest(promotionProduct.getName(), additional)))) {
            order.getAdditionalPromotion(additional);
        }
    }

    private void decideExcludeNonPromotion(Order order, int additional) {
        Product promotionProduct = order.getProduct();
        if (!retryIfHasError(() -> inputView.readContinuePurchase(
                new RemoveNonPromotionRequest(promotionProduct.getName(),
                        -additional)))) {
            order.removeNonPromotionQuantity();
        }
    }

    private void applyMembership(Receipt receipt) {
        Boolean isMembership = retryIfHasError(inputView::readMembership);
        if (isMembership) {
            receipt.applyMembershipDiscount();
        }
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
}
