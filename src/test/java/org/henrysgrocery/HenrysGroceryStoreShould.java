package org.henrysgrocery;

import org.henrysgrocery.store.Basket;
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
}