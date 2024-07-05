package pl.ulianak.ecommerce.sales.reservation;

import pl.ulianak.ecommerce.sales.payment.PaymentDetails;
import pl.ulianak.ecommerce.sales.payment.PaymentGateway;
import pl.ulianak.ecommerce.sales.payment.RegisterPaymentRequest;

import java.util.UUID;

public class SpyPaymentGateway implements PaymentGateway {
    Integer requestCount = 0;
    public Integer getRequestsCount() {
        return requestCount;
    }

    @Override
    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest) {
        this.requestCount++;
        return new PaymentDetails("http://spy-gateway", UUID.randomUUID().toString());
    }
}
