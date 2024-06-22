package pl.ulianak.ecommerce.sales;

import pl.ulianak.ecommerce.sales.cart.Cart;
import pl.ulianak.ecommerce.sales.reservation.Reservation;
import pl.ulianak.ecommerce.sales.reservation.ReservationRepository;
import pl.ulianak.ecommerce.sales.cart.InMemoryCartStorage;
import pl.ulianak.ecommerce.sales.offering.Offer;
import pl.ulianak.ecommerce.sales.offering.OfferCalculator;
import pl.ulianak.ecommerce.sales.payment.PaymentDetails;
import pl.ulianak.ecommerce.sales.payment.PaymentGateway;
import pl.ulianak.ecommerce.sales.payment.RegisterPaymentRequest;
import pl.ulianak.ecommerce.sales.reservation.AcceptOfferRequest;
import pl.ulianak.ecommerce.sales.reservation.ReservationDetails;

import java.util.UUID;


public class SalesFacade {
    private OfferCalculator offerCalculator;
    private InMemoryCartStorage cartStorage;
    private PaymentGateway paymentGateway;
    private ReservationRepository reservationRepository;

    public SalesFacade(InMemoryCartStorage cartStorage, OfferCalculator offerCalculator, PaymentGateway paymentGateway, ReservationRepository reservationRepository){
        this.cartStorage = cartStorage;
        this.offerCalculator = offerCalculator;
        this.paymentGateway = paymentGateway;
        this.reservationRepository = reservationRepository;
    }

    public void addToCart(String customerId, String productId) {
        Cart cart = loadCartForCustomer(customerId);

        cart.addProduct(productId);

        cartStorage.save(customerId, cart);
    }

    private Cart loadCartForCustomer(String customerId) {
        return cartStorage.findByCustomerId(customerId)
                .orElse(Cart.empty());
    }

    public Offer getCurrentOffer(String customerId) {
        Cart cart = loadCartForCustomer(customerId);
        Offer offer = offerCalculator.calculate(cart.getLines());

        return offer;
    }

    public ReservationDetails acceptOffer(String customerId, AcceptOfferRequest acceptOfferRequest) {
        String reservationId = UUID.randomUUID().toString();
        Offer offer = this.getCurrentOffer(customerId);

        PaymentDetails paymentDetails = paymentGateway.registerPayment(
                RegisterPaymentRequest.of(reservationId, acceptOfferRequest, offer.getTotal())
        );

        Reservation reservation = Reservation.of(
                reservationId,
                customerId,
                acceptOfferRequest,
                offer,
                paymentDetails);

        reservationRepository.add(reservation);

        return new ReservationDetails(reservationId, paymentDetails.getPaymentUrl());
    }
}
