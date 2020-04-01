package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Item> items = new ArrayList<>();

    public void add(int quantity, Item item) {
        for (int i = 0; i < quantity; i++)
            items.add(item);
    }

    public BigDecimal priceUp() {
        BigDecimal total = items.stream()
                             .map(i -> i.price)
                             .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }
}
