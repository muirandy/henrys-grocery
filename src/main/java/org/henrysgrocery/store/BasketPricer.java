package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.Stream;

class BasketPricer {

    public static BasketPricer forDay(LocalDate today) {
        return new BasketPricer(today);
    }

    private LocalDate purchaseDate;

    private BasketPricer(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal priceUp(Stream<Item> items) {
        return items
                .map(i -> i.price)
                .map(p -> applyDiscount(purchaseDate, p))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal applyDiscount(LocalDate purchaseDate, BigDecimal p) {
        if (discountApplies(purchaseDate))
            return p.multiply(BigDecimal.valueOf(0.9));
        return p;
    }

    private boolean discountApplies(LocalDate purchaseDate) {
        LocalDate today = LocalDate.now();
        LocalDate dayAfterEndOfNextMonth = today.plusMonths(2).with(TemporalAdjusters.firstDayOfMonth());
        return purchaseDate.isAfter(today.plusDays(2))
                && purchaseDate.isBefore(dayAfterEndOfNextMonth);
    }
}
