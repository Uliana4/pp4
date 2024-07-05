package pl.ulianak.ecommerce.catalog;

import java.util.HashMap;
import java.util.List;

public class HasMapProductStorage implements ProductStorage{
    HashMap<String, Product> products;

    public HasMapProductStorage() {
        this.products = new HashMap<>();
    }

    @Override
    public void add(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public void setUpDatabase() {

    }

    @Override
    public Product getProductById(String id) {
        return products.get(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return products.values().stream().toList();
    }
}
