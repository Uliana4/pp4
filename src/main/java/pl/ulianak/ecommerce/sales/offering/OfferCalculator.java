package pl.ulianak.ecommerce.sales.offering;

import org.springframework.beans.factory.annotation.Autowired;
import pl.ulianak.ecommerce.sales.cart.CartLine;
import pl.ulianak.ecommerce.catalog.ProductCatalog;
import pl.ulianak.ecommerce.catalog.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OfferCalculator {
    private ProductCatalog catalog;

    @Autowired
    public OfferCalculator(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    public Offer calculate(List<CartLine> lines) {
        int quantitySum = 0;
        List<BigDecimal> finalPriceArray = new ArrayList<>();

        for (CartLine cartLine : lines) {
            quantitySum += cartLine.getQty();
            Product product = catalog.getProductBy(cartLine.getProductId());
            BigDecimal productPrice = product.getPrice();
            int nthForFree = 5; // every 5nth product for free
            int quantity = cartLine.getQty() - cartLine.getQty() / nthForFree;
            BigDecimal lineTotal = BigDecimal.valueOf(quantity).multiply(productPrice);
            finalPriceArray.add(lineTotal);
        }

        BigDecimal finalPrice = finalPriceArray.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal finalPriceToReturn;
        if (finalPrice.compareTo(BigDecimal.valueOf(100)) >= 0) {
            finalPriceToReturn = finalPrice.multiply(BigDecimal.valueOf(0.9)); // 10% discount
        } else {
            finalPriceToReturn = finalPrice;
        }
        return new Offer(finalPriceToReturn, quantitySum);
    }
}
