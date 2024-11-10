package store.view;

import java.util.List;
import store.dto.ProductShowResponse;
import store.model.Order;
import store.model.Product;
import store.model.PromotionPolicy;
import store.model.Receipt;

public class OutputView {

    public void printProducts(List<ProductShowResponse> responses) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
        responses.forEach(System.out::println);
    }

    public void showError(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }

    public void showReceipt(Receipt receipt) {
        List<Order> orders = receipt.getOrders();
        System.out.println("==============W 편의점================");
        showPurchaseProducts(orders);
        showPromotionGift(orders);
        showResult(receipt, orders);
    }

    private void showResult(Receipt receipt, List<Order> orders) {
        int totalCount = orders.stream().mapToInt(Order::getTotalQuantity).sum();
        System.out.println("===================================");
        System.out.printf("%s%-10d%-,10d\n", convert("총구매액", 16), totalCount, receipt.getTotalPrice());
        System.out.printf("%s%-,10d\n", convert("행사할인", 26), -receipt.getPromotionDiscount());
        System.out.printf("%s%-,10d\n", convert("멤버십할인", 26), -receipt.getMembershipDiscount());
        System.out.printf("%s%-,10d\n", convert("내실돈", 26),
                receipt.getTotalPrice() - receipt.getPromotionDiscount() - receipt.getMembershipDiscount());
    }

    private void showPromotionGift(List<Order> orders) {
        System.out.println("=============증     정===============");
        for (Order order : orders) {
            if (order.getPromotionQuantity() > 0) {
                PromotionPolicy policy = order.getPromotion().getPolicy();
                int freeCount = order.getPromotionQuantity() / (policy.getBuy() + policy.getGet());
                System.out.printf("%s%-10d\n", convert(order.getProduct().getName(), 16), freeCount);
            }
        }
    }

    private void showPurchaseProducts(List<Order> orders) {
        System.out.printf("%s%s%s%n", convert("상품명", 16), convert("수량", 10), convert("금액", 10));
        for (Order order : orders) {
            Product product = order.getProduct();
            System.out.printf("%s%-10d%,-10d%n", convert(product.getName(), 16), order.getTotalQuantity(),
                    order.getTotalQuantity() * product.getPrice());
        }
    }

    private int getKorCnt(String kor) {
        int cnt = 0;
        for (int i = 0; i < kor.length(); i++) {
            if (kor.charAt(i) >= '가' && kor.charAt(i) <= '힣') {
                cnt++;
            }
        }
        return cnt;
    }

    public String convert(String word, int size) {
        String formatter = String.format("%%-%ds", size - getKorCnt(word));
        return String.format(formatter, word);
    }
}
