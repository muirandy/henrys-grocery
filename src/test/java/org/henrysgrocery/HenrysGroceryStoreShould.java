package org.henrysgrocery;

import org.henrysgrocery.store.Basket;
import org.henrysgrocery.store.Item;
import org.henrysgrocery.store.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HenrysGroceryStoreShould {
    private static final Item MILK = new Item(Unit.BOTTLE, "Milk", 1.30);
    private static final Item APPLE = new Item(Unit.SINGLE, "Apples", 0.10);

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate THREE_DAYS_HENCE = TODAY.plusDays(3);

    private Basket basket = new Basket();

    @Test
    void emptyBasket() {
        BigDecimal total = basket.priceUp(TODAY);

        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void singleMilk() {
        basket.add(1, MILK);

        BigDecimal total = basket.priceUp(TODAY);

        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(1.30));
    }

    @Test
    void multipleMilk() {
        basket.add(3, MILK);

        BigDecimal total = basket.priceUp(TODAY);

        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(3.90));
    }

    @Test
    void differentItems() {
        basket.add(1, MILK);
        basket.add(1, APPLE);

        BigDecimal total = basket.priceUp(TODAY);

        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(1.40));
    }

    @Test
    void discountedApple() {
        basket.add(1, APPLE);

        BigDecimal total = basket.priceUp(THREE_DAYS_HENCE);

        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(0.09));
    }
}