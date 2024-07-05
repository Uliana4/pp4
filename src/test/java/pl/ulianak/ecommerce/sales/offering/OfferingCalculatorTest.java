package pl.ulianak.ecommerce.sales.offering;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.ulianak.ecommerce.catalog.ProductCatalog;
import pl.ulianak.ecommerce.sales.cart.Cart;
import pl.ulianak.ecommerce.sales.cart.CartLine;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OfferingCalculatorTest {


    @Autowired
    private OfferCalculator offerCalculator;
    @Autowired
    ProductCatalog catalog;

    @Test
    void itCalculatesOfferS1(){
        Cart cart = Cart.empty();
        catalog.setUpDatabase();
        catalog.addProduct("Lego set 8083" , "Nice one", BigDecimal.valueOf(10));
        catalog.addProduct("Cobi Blocks" , "Nice one" , BigDecimal.valueOf(5));
        String productId1 = catalog.allProducts().get(0).getId();
        String productId2 = catalog.allProducts().get(1).getId();

        for (int i = 0; i < 11; i++) {
            cart.addProduct(productId1);
        }

        for (int i = 0; i < 5; i++) {
            cart.addProduct(productId2);
        }

        List<CartLine> lines = cart.getLines();
        var result = offerCalculator.calculate(lines);

        assertThat(result.getItemsCount())
                .isEqualTo(16);
        assertThat(result.getTotal())
                .isEqualTo(new BigDecimal("99.00"));
    }

    @Test
    void itCalculatesOfferS2(){
        Cart cart = Cart.empty();
        catalog.setUpDatabase();
        catalog.addProduct("Lego set 8083" , "Nice one", BigDecimal.valueOf(10));
        catalog.addProduct("Cobi Blocks" , "Nice one" , BigDecimal.valueOf(5));
        String productId1 = catalog.allProducts().get(0).getId();
        String productId2 = catalog.allProducts().get(1).getId();

        for (int i = 0; i < 10; i++) {
            cart.addProduct(productId1);
        }

        for (int i = 0; i < 5; i++) {
            cart.addProduct(productId2);
        }

        List<CartLine> lines = cart.getLines();
        var result = offerCalculator.calculate(lines);

        assertThat(result.getItemsCount())
                .isEqualTo(15);
        assertThat(result.getTotal())
                .isEqualTo(new BigDecimal("90.00"));
    }

    @Test
    void itCalculatesOfferS3(){
        Cart cart = Cart.empty();
        catalog.setUpDatabase();
        catalog.addProduct("Lego set 8083" , "Nice one", BigDecimal.valueOf(10));
        catalog.addProduct("Cobi Blocks" , "Nice one" , BigDecimal.valueOf(5));
        String productId1 = catalog.allProducts().get(0).getId();
        String productId2 = catalog.allProducts().get(1).getId();

        for (int i = 0; i < 9; i++) {
            cart.addProduct(productId1);
        }

        for (int i = 0; i < 4; i++) {
            cart.addProduct(productId2);
        }

        List<CartLine> lines = cart.getLines();
        var result = offerCalculator.calculate(lines);

        assertThat(result.getItemsCount())
                .isEqualTo(13);
        assertThat(result.getTotal())
                .isEqualTo(new BigDecimal("90.00"));
    }

    private String thereIsProduct(String id) {
        return id;
    }
}
