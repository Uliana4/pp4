package pl.ulianak.ecommerce.catalog;

import java.util.List;

public interface ProductStorage {
    List<Product> getAllProducts();

    void add(Product newProduct);
    void setUpDatabase();

    Product getProductById(String id);
}
