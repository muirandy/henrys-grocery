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

        assertBasketValue(total, 0);
    }

    @Test
    void singleMilk() {
        BigDecimal total = basket.add(1, MILK)
                                 .priceUp(TODAY);

        assertBasketValue(total, 1.30);
    }

    @Test
    void multipleMilk() {
        BigDecimal total = basket.add(3, MILK)
                                 .priceUp(TODAY);

        assertBasketValue(total, 3.90);
    }

    @Test
    void differentItems() {
        BigDecimal total = basket.add(1, MILK)
                                 .add(1, APPLE)
                                 .priceUp(TODAY);

        assertBasketValue(total, 1.40);
    }

    @Test
    void discountedApple() {
        BigDecimal total = basket.add(1, APPLE)
                                 .priceUp(THREE_DAYS_HENCE);

        assertBasketValue(total, 0.09);
    }

    private void assertBasketValue(BigDecimal total, double val) {
        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(val));
    }
}