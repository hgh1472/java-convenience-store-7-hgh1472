package store.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.exception.NoProductException;
import store.model.Product;

public class ProductRepository {
    private Map<String, Product> products;

    public ProductRepository() {
        this.products = new HashMap<>();
    }

    public void save(Product product) {
        if (!products.containsKey(product.getName())) {
            products.put(product.getName(), product);
            return;
        }
        Product before = products.get(product.getName());
        if (isDefaultProduct(product)) {
            before.registerDefaultProduct(product.getDefaultQuantity());
            return;
        }
        before.registerPromotionProduct(product.getPromotionQuantity(), product.getPromotionName());
    }

    private boolean isDefaultProduct(Product product) {
        return product.getPromotionName().equals("null");
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Product findByName(String name) {
        if (products.containsKey(name)) {
            return products.get(name);
        }
        throw new NoProductException();
    }
}
