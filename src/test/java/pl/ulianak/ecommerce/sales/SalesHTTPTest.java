package pl.ulianak.ecommerce.sales;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.springframework.web.servlet.function.EntityResponse;
import pl.ulianak.ecommerce.catalog.ProductCatalog;
import pl.ulianak.ecommerce.sales.offering.AcceptOfferRequest;
import pl.ulianak.ecommerce.sales.reservation.ReservationDetails;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalesHTTPTest {
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate http;

    @Autowired
    ProductCatalog catalog;


    @Test
    void itAcceptsOfferHappyPath() throws JsonProcessingException {
        var productId = thereIsExampleProduct("Example product", "Description", BigDecimal.valueOf(10));
        //ACT
        //add to car
        var uri = String.format("api/add-to-cart/%s", productId);
        var addProductToCartUrl = String.format("http://localhost:%s/%s", port, uri);

        http.postForEntity(addProductToCartUrl, null, Object.class);

        //ACT
        //accept offer
        AcceptOfferRequest acceptOfferRequest = new AcceptOfferRequest();
        acceptOfferRequest
                .setFirstname("John")
                .setLastname("Doe")
                .setEmail("john.doe@gmail.com");

        var acceptOfferUrl = String.format("http://localhost:%s/%s", port, "api/accept-offer");
        ResponseEntity<String> reservationResponse = http.postForEntity(acceptOfferUrl, acceptOfferRequest, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ReservationDetails response = objectMapper.readValue(reservationResponse.getBody(), ReservationDetails.class);


        assertEquals(HttpStatus.OK, reservationResponse.getStatusCode());
        assertEquals(new BigDecimal("10.0"), response.getTotal());
        assertNotNull(response.getReservationId());
        assertNotNull(response.getPaymentUrl());
    }

    private Object thereIsExampleProduct(String name, String description, BigDecimal price) {
        var prodId = catalog.addProduct(name, description, price);
        return prodId;
    }
}