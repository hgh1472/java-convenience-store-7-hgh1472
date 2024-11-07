package store;

import java.util.ArrayList;
import store.controller.ConvenienceController;
import store.model.Convenience;
import store.view.OutputView;
import store.view.Reader;

public class Application {
    public static void main(String[] args) {
        ConvenienceController convenienceController = new ConvenienceController(
                new Reader(),
                new Convenience(new ArrayList<>(), new ArrayList<>()),
                new OutputView());
        convenienceController.run();
    }
}
