package pl.ulianak.ecommerce.sales;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.ulianak.ecommerce.catalog.ProductCatalog;
import pl.ulianak.ecommerce.sales.offering.Offer;
import pl.ulianak.ecommerce.sales.offering.AcceptOfferRequest;
import pl.ulianak.ecommerce.sales.reservation.ReservationDetails;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalesHTTPTest {
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate http;

    @Autowired
    ProductCatalog catalog;

    @Test
    void itAcceptOfferHappyPath(){
        var productId = thereIsExampleProduct("Example product", BigDecimal.valueOf(10.10));
        var uri = String.format("api/add-to-cart/%s", productId);
        var addProductToCartUrl = String.format("http://localhost:%s/%s", port, uri);

        ResponseEntity<Object> addToCartResponse = http.postForEntity(addProductToCartUrl, null, null);

        var getCurrentOfferUrl = asBaseURL("api/current-offer");
        ResponseEntity<Offer> offerResponse = http.getForEntity(getCurrentOfferUrl, Offer.class);

        AcceptOfferRequest acceptOfferRequest = new AcceptOfferRequest();
        acceptOfferRequest
                .setFirstname("Uliana")
                .setLastname("Kutylovskaya")
                .setEmail("uliana.kutylovskaya@gmail.com");

        var acceptOfferUrl = String.format("http://localhost:%s/%s", port, "api/accept-offer");

        ResponseEntity<ReservationDetails> reservationResponse = http.postForEntity(acceptOfferUrl, acceptOfferRequest, ReservationDetails.class);

        var reservationDetails =reservationResponse.getBody();

        assertEquals(HttpStatus.OK, reservationResponse.getStatusCode());
        assertEquals(BigDecimal.valueOf(10), reservationResponse.getBody().getTotal());
        assertNotNull(reservationResponse.getBody().getReservationId());
        assertNotNull(reservationResponse.getBody().getPaymentUrl());

        assertThat(addToCartResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(offerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reservationResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(reservationDetails.getPaymentUrl()).isNotBlank();
        assertThat(reservationDetails.getReservationId()).isNotBlank();
    }

    private String thereIsExampleProduct(String name, BigDecimal price) {
        var prodId = catalog.addProduct(name, name);
        catalog.changePrice(prodId, price);

        return prodId;
    }

    private String asBaseURL(String addToCartUri) {
        return String.format("http://localhost:%s/%s", port, addToCartUri);
    }
}
