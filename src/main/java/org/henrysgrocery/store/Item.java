package org.henrysgrocery.store;

import java.math.BigDecimal;

public class Item {
    final Unit unit;
    final String name;
    final BigDecimal price;

    public Item(Unit unit, String name, double price) {
        this.unit = unit;
        this.name = name;
        this.price = BigDecimal.valueOf(price);
    }
}
