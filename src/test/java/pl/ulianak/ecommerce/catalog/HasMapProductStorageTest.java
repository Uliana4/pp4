package pl.ulianak.ecommerce.catalog;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;

public class HasMapProductStorageTest {
    @Test
    void itStoreNewProduct(){
        ProductStorage storage = thereIsProductStorage();
        Product product = thereIsExampleProduct();
        storage.add(product);
        List<Product> products = storage.allProducts();
        assertThat(products)
                .hasSize(1)
                .extracting(Product::getName)
                .contains("test product");
    }

    private Product thereIsExampleProduct(){
        return new Product(UUID.randomUUID(), "test product", "nazwa");
    }

    private ProductStorage thereIsProductStorage(){
        return new HasMapProductStorage();
    }

}
