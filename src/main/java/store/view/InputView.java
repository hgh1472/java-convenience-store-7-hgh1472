package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import store.dto.AdditionalQuantityRequest;
import store.dto.OrderRequest;
import store.dto.RemoveNonPromotionRequest;

public class InputView {
    private static final String DELIMITER = ",";
    private static final String ORDER_REQUEST_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String ADDITIONAL_QUANTITY_MESSAGE = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    private static final String CONTINUE_PURCHASE_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";

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

    public Boolean readAdditionalQuantity(AdditionalQuantityRequest additionalQuantityRequest) {
        System.out.printf(ADDITIONAL_QUANTITY_MESSAGE, additionalQuantityRequest.getProductName(),
                additionalQuantityRequest.getAdditionalQuantity());
        return readYesOrNo();
    }

    public Boolean readContinuePurchase(RemoveNonPromotionRequest removeNonPromotionRequest) {
        System.out.printf(CONTINUE_PURCHASE_MESSAGE, removeNonPromotionRequest.getProductName(),
                removeNonPromotionRequest.getQuantity());
        return readYesOrNo();
    }

    private Boolean readYesOrNo() {
        String input = Console.readLine();
        if (input.equals("Y")) {
            return true;
        }
        if (input.equals("N")) {
            return false;
        }
        throw new IllegalArgumentException("[ERROR] Y 또는 N을 입력해 주세요.");
    }

    public Boolean readMembership() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return readYesOrNo();
    }

    public Boolean isContinuePurchase() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return readYesOrNo();
    }
}
