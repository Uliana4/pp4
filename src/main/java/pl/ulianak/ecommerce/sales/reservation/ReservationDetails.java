package pl.ulianak.ecommerce.sales.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ReservationDetails {
    private String reservationId;
    private String paymentUrl;
    private BigDecimal total;

    public ReservationDetails(@JsonProperty("reservationId") String reservationId,
                              @JsonProperty("paymentURL") String paymentUrl,
                              @JsonProperty("total") BigDecimal total) {
        this.reservationId = reservationId;
        this.paymentUrl = paymentUrl;
        this.total = total;
    }

    public BigDecimal getTotal(){
        return total;
    }

    public ReservationDetails setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public String getReservationId(){
        return reservationId;
    }

    public ReservationDetails setReservationId(String reservationId) {
        this.reservationId = reservationId;
        return this;
    }

    public ReservationDetails() {
    }

    public String getPaymentUrl(){
        return paymentUrl;
    }

    public ReservationDetails setPaymentURL(String paymentUrl) {
        this.paymentUrl = paymentUrl;
        return this;
    }
}
