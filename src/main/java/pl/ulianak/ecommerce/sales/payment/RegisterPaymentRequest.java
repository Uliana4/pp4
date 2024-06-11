package pl.ulianak.ecommerce.sales.payment;

import pl.ulianak.ecommerce.sales.reservation.AcceptOfferRequest;

import java.math.BigDecimal;

public class RegisterPaymentRequest {
    public RegisterPaymentRequest(String reservationId, AcceptOfferRequest)

    public static RegisterPaymentRequest of(String reservationId, AcceptOfferRequest acceptOfferRequest, BigDecimal total) {
        return null;
    }

    public Integer getTotalAsPennies() {
        return total.multiply(new BigDecimal((100)).intValue());
    }
}
