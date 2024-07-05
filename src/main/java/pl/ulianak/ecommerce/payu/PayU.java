package pl.ulianak.ecommerce.payu;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.ulianak.ecommerce.sales.payment.PaymentDetails;
import pl.ulianak.ecommerce.sales.payment.PaymentGateway;
import pl.ulianak.ecommerce.sales.payment.RegisterPaymentRequest;

import java.util.Arrays;
import java.util.UUID;


public class PayU implements PaymentGateway {
    RestTemplate http;
    private final PayUCredentials credentials;

    public PayU(RestTemplate http, PayUCredentials credentials){
        this.http = http;
        this.credentials = credentials;
    }

    public OrderCreateResponse handle(OrderCreateRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization",
                String.format("Bearer %s", getToken())
        );

        HttpEntity<OrderCreateRequest> headerAwareRequest = new HttpEntity<>(request, headers);

        ResponseEntity<OrderCreateResponse> response = http.postForEntity(
                String.format("%s/api/v2_1/orders", credentials.getBaseUrl()),
                headerAwareRequest,
                OrderCreateResponse.class);

        return response.getBody();
    }

    private String getToken() {
        String body = String.format(
                "grant_type=client_credentials&client_id=%s&client_secret=%s",
                credentials.getClientId(),
                credentials.getClientSecret()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        var url = String.format("%s/pl/standard/user/oauth/authorize", credentials.getBaseUrl());
        ResponseEntity<AuthorizationResponse> response = http.postForEntity(url, request, AuthorizationResponse.class);

        return response.getBody().getAccessToken();
    }

    @Override
    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest) {
        var request = new OrderCreateRequest();
        request
                .setNotifyUrl("https://my.example.shop.ukut.pl/api/order")
                .setCustomerIp("127.0.0.1")
                .setMerchantPosId("300746")
                .setDescription("My ebook")
                .setCurrencyCode("PLN")
                .setTotalAmount(registerPaymentRequest.getTotalAsPennies())
                .setExtOrderId(UUID.randomUUID().toString())
                .setBuyer((new Buyer())
                        .setEmail(registerPaymentRequest.getEmail())
                        .setFirstName(registerPaymentRequest.getFirstname())
                        .setLastName(registerPaymentRequest.getLastname())
                        .setLanguage("pl")
                )
                .setProducts(Arrays.asList(
                        new Product()
                                .setName("Product X")
                                .setQuantity(1)
                                .setUnitPrice(210000)
                ));

        OrderCreateResponse response = this.handle(request);
        return new PaymentDetails(response.getRedirectUri(), response.getOrderId());
    }
}
