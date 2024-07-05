package pl.ulianak.ecommerce.sales.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.ulianak.ecommerce.catalog.ProductCatalog;
import pl.ulianak.ecommerce.sales.SalesFacade;
import pl.ulianak.ecommerce.sales.cart.InMemoryCartStorage;
import pl.ulianak.ecommerce.sales.offering.AcceptOfferRequest;
import pl.ulianak.ecommerce.sales.offering.OfferCalculator;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class OfferAcceptanceTest {

    private SpyPaymentGateway spyPaymentGateway;
    private ReservationRepository reservationRepository;

    @Autowired
    ProductCatalog catalog;

    @BeforeEach
    void setUp(){
        spyPaymentGateway = new SpyPaymentGateway();
        reservationRepository = new ReservationRepository();
    }
    @Test
    void itAllowsToAcceptAnOffer(){
        SalesFacade sales = thereIsSales();
        String customerId = thereIsCustomer("Wojtek");
        String productId= thereIsProduct("Product 1", "Desc", BigDecimal.valueOf(10));

        sales.addToCart(customerId, productId);
        sales.addToCart(customerId, productId);

        var acceptOfferRequest = new AcceptOfferRequest();
        acceptOfferRequest
                .setFirstname("john")
                .setLastname("doe")
                .setEmail("jd@example.com");
        ReservationDetails reservationDetails = sales.acceptOffer(customerId, acceptOfferRequest);

        assertThat(reservationDetails.getPaymentUrl()).isNotBlank();
        assertThat(reservationDetails.getReservationId()).isNotBlank();

        assertPaymentHasBeenRegistered();
        assertThereIsReservationWithId(reservationDetails.getReservationId());
        assertReservationIsPending(reservationDetails.getReservationId());
        assertReservationIsDoneForCustomer(reservationDetails.getReservationId(), "john", "doe", "jd@example.com");
        assertReservationTotalMatchOffer(reservationDetails.getReservationId(), BigDecimal.valueOf(20.0));
    }

    private void assertReservationTotalMatchOffer(String reservationId, BigDecimal expectedTotal) {
        Reservation loaded = reservationRepository.load(reservationId)
                .get();
        assertThat(loaded.getTotal()).isEqualTo(expectedTotal);
    }

    private void assertReservationIsDoneForCustomer(String reservationId, String firstname, String lastname, String email) {
        Reservation loaded = reservationRepository.load(reservationId)
                .get();

        CustomerDetails clientData = loaded.getCustomerDetails();
        assertThat(clientData.getFirstName()).isEqualTo(firstname);
        assertThat(clientData.getLastName()).isEqualTo(lastname);
        assertThat(clientData.getEmail()).isEqualTo(email);
    }

    private void assertReservationIsPending(String reservationId) {
        Reservation loaded = reservationRepository.load(reservationId)
                .get();

        assertThat(loaded.isPending()).isTrue();
    }

    private void assertThereIsReservationWithId(String reservationId) {
        Optional<Reservation> loaded = reservationRepository.load(reservationId);
        assertThat(loaded).isPresent();
    }

    private void assertPaymentHasBeenRegistered() {
        assertThat(spyPaymentGateway.getRequestsCount()).isEqualTo(1);
    }

    private String thereIsProduct(String name, String desc, BigDecimal price) {
        return catalog.addProduct(name, desc, price);
    }

    private String thereIsCustomer(String id) {
        return id;
    }

    private SalesFacade thereIsSales() {
        return new SalesFacade(
                new InMemoryCartStorage(),
                new OfferCalculator(catalog),
                spyPaymentGateway,
                reservationRepository
        );
    }
}