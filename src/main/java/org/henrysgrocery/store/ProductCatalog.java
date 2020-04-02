package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.util.Map;

public class ProductCatalog {
    public static ProductCatalog createPriceService() {
        return new ProductCatalog();
    }

    private ProductCatalog() {
    }

    private Map<Item, BigDecimal> catalog = Map.of(
            new Item(Unit.TIN, "soup"), BigDecimal.valueOf(0.65),
            new Item(Unit.LOAF, "bread"), BigDecimal.valueOf(0.80),
            new Item(Unit.BOTTLE, "milk"), BigDecimal.valueOf(1.30),
            new Item(Unit.SINGLE, "apples"), BigDecimal.valueOf(0.10)
    );

    public BigDecimal getPrice(Item item) {
        return catalog.get(item);
    }
}
