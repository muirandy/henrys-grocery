package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

class ApplePromotion implements Promotion {
    private final LocalDate startDate;
    private final LocalDate endDate;

    ApplePromotion(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public BigDecimal apply(List<Item> items) {
        long numberOfApples = items.stream()
                           .filter(i -> i.name.equals(ProductCatalog.APPLES))
                           .count();
        return BigDecimal.valueOf(0.01).multiply(BigDecimal.valueOf(numberOfApples));
    }

    @Override
    public boolean applies(LocalDate purchaseDate) {
        return purchaseDate.isAfter(startDate.minusDays(1))
                && purchaseDate.isBefore(endDate.plusDays(1));
    }
}
