package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.util.List;

public class ApplePromotion {
    public BigDecimal apply(List<Item> items) {
        long numberOfApples = items.stream()
                           .filter(i -> i.milk.equals("Apples"))
                           .count();
        return BigDecimal.valueOf(0.01).multiply(BigDecimal.valueOf(numberOfApples));
    }
}
