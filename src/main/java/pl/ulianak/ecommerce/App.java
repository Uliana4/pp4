package pl.ulianak.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pl.ulianak.ecommerce.catalog.ProductCatalog;
import pl.ulianak.ecommerce.catalog.SqlProductStorage;
import pl.ulianak.ecommerce.payu.PayU;
import pl.ulianak.ecommerce.payu.PayUCredentials;
import pl.ulianak.ecommerce.sales.offering.OfferCalculator;
import pl.ulianak.ecommerce.sales.SalesFacade;
import pl.ulianak.ecommerce.sales.cart.InMemoryCartStorage;
import pl.ulianak.ecommerce.sales.payment.PaymentGateway;
import pl.ulianak.ecommerce.sales.reservation.ReservationRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class App {
    public static void main(String [] args){
        System.out.println("Here we go!");
        SpringApplication.run(App.class,args);
    }

    @Bean
    public SqlProductStorage sqlProductStorage() {
        return new SqlProductStorage();
    }

    @Bean
    ProductCatalog createMyProductCatalog(SqlProductStorage sqlProductStorage){
        var catalog = new ProductCatalog(sqlProductStorage);
        catalog.setUpDatabase();

        catalog.addProduct("Lego duplo", "Duży zestaw klocków LEGO", BigDecimal.valueOf(16));
        catalog.addProduct("Monopolia", "Gra rodzinna", BigDecimal.valueOf(17));
        catalog.addProduct("Barbie dreamhouse", "Duży domek dla lalek z meblami i akcesoriami", BigDecimal.valueOf(20));
        catalog.addProduct("Nerf N-Strike Elite Disruptor", "Popularny blaster na piankowe strzałki do ekscytującej, aktywnej zabawy", BigDecimal.valueOf(39.99));
        catalog.addProduct("Play-Doh Modeling Compound 36-Pack", "Duży zestaw Play-Doh w różnych kolorach do niekończącej się kreatywnej rzeźby i modelowania.", BigDecimal.valueOf(79.99));
        catalog.addProduct("Melissa & Doug Deluxe Wooden Railway Train Set", "Wysokiej jakości drewniana kolejka z różnymi elementami torów i akcesoriam", BigDecimal.valueOf(49.79));
        catalog.addProduct("Fisher-Price Laugh & Learn Smart Stages Chair", "Interaktywne krzesełko edukacyjne dla maluchów z piosenkami", BigDecimal.valueOf(100.01));
        catalog.addProduct("Crayola Inspiration Art Case Coloring Set", "Kompletny zestaw artystyczny, który zawiera kredki, flamastry, ołówki i papier", BigDecimal.valueOf(129.89));
        catalog.addProduct("Hasbro Connect 4 Game", "Klasyczna gra strategiczna dla dwóch graczy", BigDecimal.valueOf(89.99));
        catalog.addProduct("Minecraft Dungeons Figures 6-Pack", "Zestaw sześciu figurek z popularnej gry Minecraft Dungeons", BigDecimal.valueOf(59.99));
        return catalog;
    }


    @Bean
    PaymentGateway createPaymentGateway(){
        return new PayU(
                new RestTemplate(),
                PayUCredentials.sandbox(
                        "300746",
                        "2ee86a66e5d97e3fadc400c9f19b065d"
                )
        );
    }

    @Bean
    SalesFacade createSales(ProductCatalog catalog){
        return new SalesFacade(
                new InMemoryCartStorage(),
                new OfferCalculator(catalog),
                createPaymentGateway(),
                new ReservationRepository()
        );
    }
}
