package org.henrysgrocery;

import org.henrysgrocery.store.Basket;
import org.henrysgrocery.store.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HenrysGroceryStoreShould {
    @Test
    void emptyBasket() {
        Basket basket = new Basket();

        BigDecimal total = basket.priceUp();

        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void singleMilk() {
        Basket basket = new Basket();
        basket.add(1, Unit.BOTTLE, "Milk");

        BigDecimal total = basket.priceUp();

        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(1.30));
    }
}