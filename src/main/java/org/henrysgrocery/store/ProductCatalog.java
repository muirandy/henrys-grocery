package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.util.Map;

public class ProductCatalog {

    public static final String SOUP = "soup";
    public static final String BREAD = "bread";
    public static final String MILK = "milk";
    public static final String APPLES = "apples";

    public static ProductCatalog createProductCatalog() {
        return new ProductCatalog();
    }

    private ProductCatalog() {
    }

    private Map<Item, BigDecimal> catalog = Map.of(
            new Item(Unit.TIN, SOUP), BigDecimal.valueOf(0.65),
            new Item(Unit.LOAF, BREAD), BigDecimal.valueOf(0.80),
            new Item(Unit.BOTTLE, MILK), BigDecimal.valueOf(1.30),
            new Item(Unit.SINGLE, APPLES), BigDecimal.valueOf(0.10)
    );

    public BigDecimal getPrice(Item item) {
        return catalog.get(item);
    }

    public Item getItem(Unit unit, String name) {
        return new Item(unit, name);
    }
}
