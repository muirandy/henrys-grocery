package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface Promotion {
    BigDecimal apply(List<Item> items);

    boolean applies(LocalDate purchaseDate);
}
