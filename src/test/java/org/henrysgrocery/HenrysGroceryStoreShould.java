package org.henrysgrocery;

import org.henrysgrocery.store.Basket;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HenrysGroceryStoreShould {
    @Test
    void nothing() {
        assertTrue(true);
    }

    @Test
    void emptyBasket() {
        Basket basket = new Basket();

        BigDecimal total = basket.priceUp();

        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }
}