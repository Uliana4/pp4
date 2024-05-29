package pl.ulianak.ecommerce.sales.reservation;

import java.math.BigDecimal;

public class ReservationDetails {
    private final String reservationId;
    private final String paymentUrl;

    public ReservationDetails(String reservationId, String paymentURL) {
        this.reservationId = reservationId;
        this.paymentUrl = paymentURL;
    }

    public BigDecimal getTotal(){
        return BigDecimal.valueOf(0);
    }

    public Object getReservationId(){
        return reservationId;
    }

    public  Object getPaymentUrl(){
        return paymentUrl;
    }
}
