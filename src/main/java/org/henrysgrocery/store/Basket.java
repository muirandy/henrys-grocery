package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        return new BasketPricer(purchaseDate).priceUp(items.stream());
    }

}
