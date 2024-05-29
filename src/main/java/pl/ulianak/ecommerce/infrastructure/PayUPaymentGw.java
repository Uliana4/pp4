package pl.ulianak.ecommerce.infrastructure;

import pl.ulianak.ecommerce.sales.payment.PaymentDetails;
import pl.ulianak.ecommerce.sales.payment.PaymentGateway;
import pl.ulianak.ecommerce.sales.payment.RegisterPaymentRequest;

public class PayUPaymentGw implements PaymentGateway {

    @Override
    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest) {
        return null;
    }
}
