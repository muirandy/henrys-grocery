package org.henrysgrocery.store;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HenrysGroceryStoreShould {
    private static final Item MILK = new Item(Unit.BOTTLE, "Milk", 1.30);
    private static final Item APPLE = new Item(Unit.SINGLE, "Apples", 0.10);
    private static final Item BREAD = new Item(Unit.LOAF, "bread", 0.80);
    private static final Item SOUP = new Item(Unit.TIN, "soup", 0.65);

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TWO_DAYS_HENCE = TODAY.plusDays(2);
    private static final LocalDate THREE_DAYS_HENCE = TODAY.plusDays(3);
    private static final LocalDate END_OF_NEXT_MONTH = TODAY.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
    private static final LocalDate DAY_AFTER_END_OF_NEXT_MONTH = END_OF_NEXT_MONTH.plusDays(1);

    private static final BasketPricer BASKET_PRICER_TODAY = BasketPricer.forDay(TODAY);
    private static final BasketPricer BASKET_PRICER_TWO_DAYS_HENCE = BasketPricer.forDay(TWO_DAYS_HENCE);
    private static final BasketPricer BASKET_PRICER_THREE_DAYS_HENCE = BasketPricer.forDay(THREE_DAYS_HENCE);
    private static final BasketPricer BASKET_PRICER_END_OF_NEXT_MONTH = BasketPricer.forDay(END_OF_NEXT_MONTH);
    private static final BasketPricer BASKET_PRICER_DAY_AFTER_END_OF_NEXT_MONTH = BasketPricer.forDay(DAY_AFTER_END_OF_NEXT_MONTH);


    private Basket basket = Basket.create();

    @Test
    void emptyBasket() {
        BigDecimal total = basket.priceUp(BASKET_PRICER_TODAY);

        assertBasketValue(total, 0);
    }

    @Test
    void singleMilk() {
        BigDecimal total = basket.add(1, MILK)
                                 .priceUp(BASKET_PRICER_TODAY);

        assertBasketValue(total, 1.30);
    }

    @Test
    void multipleMilk() {
        BigDecimal total = basket.add(3, MILK)
                                 .priceUp(BASKET_PRICER_TODAY);

        assertBasketValue(total, 3.90);
    }

    @Test
    void differentItems() {
        BigDecimal total = basket.add(1, MILK)
                                 .add(1, APPLE)
                                 .priceUp(BASKET_PRICER_TODAY);

        assertBasketValue(total, 1.40);
    }

    @Test
    void discountedApple() {
        assertBasketValue(basket.add(1, APPLE)
                                .priceUp(BASKET_PRICER_THREE_DAYS_HENCE), 0.09);
        assertBasketValue(Basket.create()
                                .add(1, APPLE)
                                .priceUp(BASKET_PRICER_END_OF_NEXT_MONTH), 0.09);
    }

    @Test
    void fullPriceApples() {
        assertBasketValue(basket.add(1, APPLE)
                                .priceUp(BASKET_PRICER_TODAY), 0.10);
        assertBasketValue(Basket.create()
                                .add(1, APPLE)
                                .priceUp(BASKET_PRICER_TWO_DAYS_HENCE), 0.10);
        assertBasketValue(Basket.create()
                                .add(1, APPLE)
                                .priceUp(BASKET_PRICER_DAY_AFTER_END_OF_NEXT_MONTH), 0.10);
    }

    @Test
    void fullPriceBread() {
        assertBasketValue(basket.add(1, BREAD)
                                .add(1, SOUP)
                                .priceUp(BASKET_PRICER_TODAY), 1.45);
    }

    private void assertBasketValue(BigDecimal total, double val) {
        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(val));
    }
}