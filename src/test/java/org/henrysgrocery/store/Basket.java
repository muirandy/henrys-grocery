package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Item> items = new ArrayList<>();

    public Basket add(int quantity, Item item) {
        for (int i = 0; i < quantity; i++)
            items.add(item);
        return this;
    }

    public BigDecimal priceUp(LocalDate purchaseDate) {
        BigDecimal total = items.stream()
                                .map(i -> i.price)
                                .map(p -> applyDiscount(purchaseDate, p))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }

    private BigDecimal applyDiscount(LocalDate purchaseDate, BigDecimal p) {
        if (purchaseDate.isAfter(LocalDate.now().plusDays(2)))
            return p.multiply(BigDecimal.valueOf(0.9));
        return p;
    }
}
