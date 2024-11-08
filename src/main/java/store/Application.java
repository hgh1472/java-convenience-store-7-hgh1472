package store;

import store.controller.ConvenienceController;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.ConvenienceService;
import store.view.InputView;
import store.view.OutputView;
import store.view.Reader;

public class Application {
    public static void main(String[] args) {
        ConvenienceController convenienceController = new ConvenienceController(
                new Reader(),
                new ConvenienceService(new ProductRepository(), new PromotionRepository()),
                new OutputView(), new InputView());
        convenienceController.run();
    }
}
