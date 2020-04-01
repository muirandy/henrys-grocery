package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Item> items = new ArrayList<>();

    public static Basket create() {
        return new Basket();
    }

    private Basket() {
    }

    public Basket add(int quantity, Item item) {
        for (int i = 0; i < quantity; i++)
            items.add(item);
        return this;
    }

    public BigDecimal priceUp(LocalDate purchaseDate) {
        return items.stream()
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

    public Basket empty() {
        items.clear();
        return this;
    }
}
