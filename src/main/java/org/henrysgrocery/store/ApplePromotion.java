package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class ApplePromotion {
    public BigDecimal apply(List<Item> items) {
        long numberOfApples = items.stream()
                           .filter(i -> i.name.equals("Apples"))
                           .count();
        return BigDecimal.valueOf(0.01).multiply(BigDecimal.valueOf(numberOfApples));
    }

    public boolean applies(LocalDate purchaseDate) {
        LocalDate today = LocalDate.now();
        LocalDate dayAfterEndOfNextMonth = today.plusMonths(2).with(TemporalAdjusters.firstDayOfMonth());
        return purchaseDate.isAfter(today.plusDays(2))
                && purchaseDate.isBefore(dayAfterEndOfNextMonth);

    }
}
