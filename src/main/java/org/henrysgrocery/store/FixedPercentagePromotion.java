package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class FixedPercentagePromotion extends DateRangePromotion {

    private static final BigDecimal ONE_HUNDREDTH = BigDecimal.valueOf(0.01);

    private final Item targetItem;
    private final BigDecimal discountMultiplier;

    private ProductCatalog productCatalog = ProductCatalog.createProductCatalog();

    FixedPercentagePromotion(LocalDate startDate, LocalDate endDate, Item targetItem, double percentageDiscount) {
        super(startDate, endDate);
        this.targetItem = targetItem;
        discountMultiplier = BigDecimal.valueOf(percentageDiscount).multiply(ONE_HUNDREDTH);
    }

    @Override
    public BigDecimal calculateTotalDiscount(List<Item> items) {
        return items.stream()
             .filter(i -> i.equals(targetItem))
             .map(i -> productCatalog.getPrice(i))
             .map(p -> p.multiply(discountMultiplier))
             .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}