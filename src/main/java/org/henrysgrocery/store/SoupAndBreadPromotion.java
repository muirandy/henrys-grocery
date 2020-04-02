package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class SoupAndBreadPromotion extends DateRangePromotion {

    private static final double DISCOUNT_MULTIPLIER = 0.5;

    private ProductCatalog productCatalog = ProductCatalog.createProductCatalog();

    SoupAndBreadPromotion(LocalDate startDate, LocalDate endDate) {
        super(startDate, endDate);
    }

    @Override
    public BigDecimal calculateTotalDiscount(List<Item> items) {
        long numberOfSoups = items.stream()
                                  .filter(i -> i.name.equals(ProductCatalog.SOUP))
                                  .count();
        long promotionRepeats = numberOfSoups / 2;

        return calculateDiscountedAmount(items, promotionRepeats);
    }

    private BigDecimal calculateDiscountedAmount(List<Item> items, long promotionRepeats) {
        return items.stream()
                    .filter(i -> i.name.equals(ProductCatalog.BREAD))
                    .limit(promotionRepeats)
                    .map(i -> productCatalog.getPrice(i))
                    .map(p -> p.multiply(BigDecimal.valueOf(DISCOUNT_MULTIPLIER)))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}