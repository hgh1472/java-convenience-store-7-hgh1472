package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import store.dto.OrderRequest;

public class InputView {
    private static final String DELIMITER = ",";
    private static final String ORDER_REQUEST_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";

    public List<OrderRequest> readOrders() {
        System.out.println(ORDER_REQUEST_MESSAGE);
        String input = Console.readLine();
        String[] orderInputs = input.split(DELIMITER);
        List<OrderRequest> orderRequests = new ArrayList<>();
        for (String orderInput : orderInputs) {
            orderRequests.add(new OrderRequest(orderInput));
        }
        return orderRequests;
    }
}
