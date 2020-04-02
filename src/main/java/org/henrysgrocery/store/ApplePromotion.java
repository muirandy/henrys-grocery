package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class ApplePromotion extends DateRangePromotion {

    ApplePromotion(LocalDate startDate, LocalDate endDate) {
        super(startDate, endDate);
    }

    @Override
    public BigDecimal apply(List<Item> items) {
        long numberOfApples = items.stream()
                           .filter(i -> i.name.equals(ProductCatalog.APPLES))
                           .count();
        return BigDecimal.valueOf(0.01).multiply(BigDecimal.valueOf(numberOfApples));
    }
}