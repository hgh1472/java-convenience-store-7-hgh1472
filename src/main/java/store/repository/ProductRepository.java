package store.repository;

import java.util.ArrayList;
import java.util.List;
import store.exception.NoProductException;
import store.exception.NoPromotionException;
import store.model.Product;

public class ProductRepository {
    private List<Product> products;

    public ProductRepository() {
        this.products = new ArrayList<>();
    }

    public void save(Product product) {
        products.add(product);
    }

    public List<Product> findAll() {
        return products;
    }

    public List<Product> findByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .toList();
    }

    public Product findDefaultProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name) && product.getPromotionName().equals("null"))
                .findAny()
                .orElseThrow(NoProductException::new);
    }

    public Product findPromotionProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name) && !product.getPromotionName().equals("null"))
                .findAny()
                .orElseThrow(NoPromotionException::new);
    }
}
