package org.henrysgrocery;

import org.henrysgrocery.store.Basket;
import org.henrysgrocery.store.Item;
import org.henrysgrocery.store.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HenrysGroceryStoreShould {
    private static final Item MILK = new Item(Unit.BOTTLE, "Milk");

    private Basket basket = new Basket();

    @Test
    void emptyBasket() {
        BigDecimal total = basket.priceUp();

        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void singleMilk() {
        basket.add(1, MILK);

        BigDecimal total = basket.priceUp();

        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(1.30));
    }

    @Test
    void multipleMilk() {
        basket.add(3, MILK);

        BigDecimal total = basket.priceUp();

        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(3.90));
    }
}