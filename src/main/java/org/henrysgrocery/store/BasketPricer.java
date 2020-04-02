package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.util.List;

class BasketPricer {

    private ProductCatalog productCatalog = ProductCatalog.createProductCatalog();
    private List<Promotion> promotions;

    BasketPricer(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public BigDecimal priceUp(List<Item> items) {
        BigDecimal baseCost = items.stream()
                                .map(i -> productCatalog.getPrice(i))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discount = calculateDiscount(items);
        return baseCost.subtract(discount);
    }

    private BigDecimal calculateDiscount(List<Item> items) {
        return promotions.stream()
                         .map(p -> p.apply(items))
                         .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}