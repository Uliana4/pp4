package pl.ulianak.ecommerce.sales;

import pl.ulianak.ecommerce.sales.cart.Cart;
import pl.ulianak.ecommerce.sales.cart.InMemoryCartStorage;


public class SalesFacade {
    private final OfferCalculator calculator;
    private InMemoryCartStorage cartStorage;

    public SalesFacade(InMemoryCartStorage cartStorage, OfferCalculator calculator){
        this.cartStorage = cartStorage;
        this.calculator = calculator;
    }

    public void addToCart(String customerId, String productId) {
        Cart cart = loadCartForCustomer(customerId);

        cart.addProduct(productId);
    }

    private Cart loadCartForCustomer(String customerId) {
        return cartStorage.findByCustomerId(customerId)
                .orElse(Cart.empty());
    }

    public Offer getCurrentOffer(String customerId) {
        Cart cart = loadCartForCustomer(customerId);
        return calculator.calculate(cart.getLines());
    }

    public ReservationDetails acceptOffer(String customerId) {
        return new ReservationDetails();
    }
}
