package pl.ulianak.ecommerce.catalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductCatalogTest {
    @Autowired
    ProductCatalog catalog;


    @BeforeEach
    void setUpDb() {
        catalog.setUpDatabase();
    }


    @Test
    void nothing(){

    }

    @Test
    void itListAvailableProducts(){
        List<Product> products = catalog.allProducts();
        assert products.isEmpty();
    }

    @Test
    void itAllowsToAddProduct(){
//        ProductCatalog catalog = new ProductCatalog(sqlProductStorage);

        List<Product> products = catalog.allProducts();

        catalog.addProduct("Lego set 8083", "Nice one", BigDecimal.valueOf(100));
        catalog.addProduct("Lego set 8083", "Nice one", BigDecimal.valueOf(100));
        catalog.addProduct("Lego set 8083", "Nice one", BigDecimal.valueOf(100));
        catalog.addProduct("Lego set 8083", "Nice one", BigDecimal.valueOf(100));
        catalog.allProducts();
        products = catalog.allProducts();

        assertThat(products)
                .hasSize(4);
    }

    @Test
    void itLoadsSingleProductById(){
//        ProductCatalog catalog = new ProductCatalog(sqlProductStorage);
        String id = catalog.addProduct("Lego set 8083", "Nice one", BigDecimal.valueOf(100));
        String id2 = catalog.addProduct("Lego set 8083", "Nice one", BigDecimal.valueOf(100));
        String id3 = catalog.addProduct("Lego set 8083", "Nice one", BigDecimal.valueOf(100));

        System.out.println("asdasdsad");
        Product loaded = catalog.getProductById(id);
        assertThat(id).isEqualTo(loaded.getId());
    }

    @Test
    void itAllowsChangePrice(){
        String id = catalog.addProduct("Lego set 8083", "Nice one", BigDecimal.valueOf(50));

        catalog.changePrice(id, BigDecimal.valueOf(10.10));
        Product loaded = catalog.getProductById(id);

        assertThat(BigDecimal.valueOf(10.10)).isEqualTo(loaded.getPrice());
    }
}
