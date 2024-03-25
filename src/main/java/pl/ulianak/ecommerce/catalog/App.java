package pl.ulianak.ecommerce.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String [] args){
        System.out.println("Here we go!");
        SpringApplication.run(App.class,args);
    }
    @Bean
    ProductCatalog createMyProductCatalog(){
        var catalog = new ProductCatalog();
        catalog.addProduct("Lego set 8083", "Nice done");
        catalog.addProduct("Cobi blocks", "Nice one");

        return catalog;
    }
}
