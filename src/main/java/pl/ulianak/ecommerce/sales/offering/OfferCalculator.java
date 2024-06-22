package pl.ulianak.ecommerce.sales.offering;

import pl.ulianak.ecommerce.sales.cart.CartLine;

import java.math.BigDecimal;
import java.util.List;

public class OfferCalculator {
    public Offer calculate(List<CartLine> lines) {
        // every 5th product - gratis
        // 100>zl to -10 pl

        return new Offer(
                BigDecimal.valueOf(10).multiply(new BigDecimal(lines.size())),
                lines.size());
    }
}
