package pl.ulianak.ecommerce;

import pl.ulianak.ecommerce.payu.*;
import pl.ulianak.ecommerce.sales.payment.PaymentDetails;
import pl.ulianak.ecommerce.sales.payment.PaymentGateway;
import pl.ulianak.ecommerce.sales.payment.RegisterPaymentRequest;

import java.util.Arrays;
import java.util.UUID;

public class PayUPaymentGateway implements PaymentGateway {
    PayU payU;

    public PayUPaymentGateway(PayU payU){
        this.payU = payU;
    }

    @Override
    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest){
        var request = new OrderCreateRequest();
        request.setNotifyUrl("https://your.eshop.com/notify")
                .setCustomerIp("*127.0.0.1*")
                .setMerchanPostId("*300746")
                .setDescription("My ebook store")
                .setCurrencyCode("*PLN")
                .setTotalAmount(registerPaymentRequest.getTotalAsPennies())
                .setExtOrderId(UUID.randomUUID().toString())
                .setBuyer(new Buyer()
                        .setEmail("uliana.kutylovskaya@example.com")
                        .setPhone("345653858")
                        .setFirstName("Uliana")
                        .setLastName("Kutylovskaya")
                        .setLanguage("pl")
                )
                .setProducts(Arrays.asList(
                        new Product()
                                .setName("Ebook x")
                                .setQuantity(1)
                                .setUnitPrice(21000)
                ));

        OrderCreateResponse response = payU.handle(request);

        return new PaymentDetails(response.getRedirectUri());
    }
}
