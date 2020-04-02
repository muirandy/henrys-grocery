package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.henrysgrocery.store.Unit.*;

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
            new Item(TIN, SOUP), BigDecimal.valueOf(0.65),
            new Item(LOAF, BREAD), BigDecimal.valueOf(0.80),
            new Item(BOTTLE, MILK), BigDecimal.valueOf(1.30),
            new Item(SINGLE, APPLES), BigDecimal.valueOf(0.10)
    );

    public BigDecimal getPrice(Item item) {
        return catalog.get(item);
    }

    public Item getItem(Unit unit, String name) {
        if (!catalog.containsKey(new Item(unit, name)))
            throw new InvalidProductException();
        return new Item(unit, name);
    }

    public List<Item> inventory() {
        return catalog.keySet().stream()
                      .sorted(Comparator.comparing(i -> i.name))
                      .collect(Collectors.toList());
    }

    public class InvalidProductException extends RuntimeException {
    }

}
