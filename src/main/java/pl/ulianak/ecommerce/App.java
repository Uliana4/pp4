package pl.ulianak.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.ulianak.ecommerce.catalog.HasMapProductStorage;
import pl.ulianak.ecommerce.catalog.ProductCatalog;
import pl.ulianak.ecommerce.sales.OfferCalculator;
import pl.ulianak.ecommerce.sales.SalesFacade;
import pl.ulianak.ecommerce.sales.cart.InMemoryCartStorage;

import java.math.BigDecimal;

@SpringBootApplication
public class App {
    public static void main(String [] args){
        System.out.println("Here we go!");
        SpringApplication.run(App.class,args);
    }
    @Bean
    ProductCatalog createMyProductCatalog(){
        var catalog = new ProductCatalog(new HasMapProductStorage());
        var pid1 = catalog.addProduct("Lego set 8083", "Nice done");
        catalog.changePrice(pid1, BigDecimal.valueOf(100.10));

        var pid2 = catalog.addProduct("Cobi blocks", "Nice one");
        catalog.changePrice(pid2, BigDecimal.valueOf(50.10));
        return catalog;
    }

    @Bean
    SalesFacade createSales(){
        return new SalesFacade(new InMemoryCartStorage(), new OfferCalculator());
    }
}
