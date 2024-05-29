package pl.ulianak.ecommerce.catalog;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;

public class HasMapProductStorageTest {
    private static final String TEST_PRODUCT_NAME = "test product";

    @Test
    void itStoreNewProduct(){
        ProductStorage storage = thereIsProductStorage();
        Product product = thereIsExampleProduct();

        storage.add(product);

        List<Product> products = storage.allProducts();

        assertThat(products)
                .hasSize(1)
                .extracting(Product::getName)
                .contains(TEST_PRODUCT_NAME);
    }

    private Product thereIsExampleProduct(){
        var product =  new Product(UUID.randomUUID(), TEST_PRODUCT_NAME, "nice one");
        product.changePrice(BigDecimal.valueOf(10.10));

        return product;
    }

    private ProductStorage thereIsProductStorage(){
        return new HasMapProductStorage();
    }

    @Test
    void itStoresAndLoadById() {
        var product = thereIsExampleProduct();
        var productStorage = thereIsProductStorage();

        productStorage.add(product);
        var loaded = productStorage.getProductBy(product.getId());

        assertThat(loaded.getId()).isEqualTo(product.getId());
    }
}
