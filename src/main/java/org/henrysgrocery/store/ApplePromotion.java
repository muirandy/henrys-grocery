package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class ApplePromotion extends DateRangePromotion {

    private final Item targetItem;

    ApplePromotion(LocalDate startDate, LocalDate endDate, Item targetItem) {
        super(startDate, endDate);
        this.targetItem = targetItem;
    }

    @Override
    public BigDecimal apply(List<Item> items) {
        long numberOfApples = items.stream()
                           .filter(i -> i.equals(targetItem))
                           .count();
        return BigDecimal.valueOf(0.01).multiply(BigDecimal.valueOf(numberOfApples));
    }
}