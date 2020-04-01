package org.henrysgrocery.store;

import java.math.BigDecimal;

public class Basket {

    private BigDecimal total = BigDecimal.ZERO;

    public void add(int quantity, Item item) {
        for (int i = 0; i < quantity; i++)
            total = total.add(BigDecimal.valueOf(1.30));
    }

    public BigDecimal priceUp() {
        return total;
    }
}
