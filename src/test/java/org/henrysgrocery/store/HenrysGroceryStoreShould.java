package org.henrysgrocery.store;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HenrysGroceryStoreShould {
    private static final Item MILK = new Item(Unit.BOTTLE, "milk");
    private static final Item APPLE = new Item(Unit.SINGLE, "apples");
    private static final Item BREAD = new Item(Unit.LOAF, "bread");
    private static final Item SOUP = new Item(Unit.TIN, "soup");

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TWO_DAYS_HENCE = TODAY.plusDays(2);
    private static final LocalDate THREE_DAYS_HENCE = TODAY.plusDays(3);
    private static final LocalDate FIVE_DAYS_HENCE = TODAY.plusDays(5);
    private static final LocalDate END_OF_NEXT_MONTH = TODAY.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
    private static final LocalDate DAY_AFTER_END_OF_NEXT_MONTH = END_OF_NEXT_MONTH.plusDays(1);

    private static final BasketPricer BASKET_PRICER_TODAY = BasketPricerCreator.forDay(TODAY);
    private static final BasketPricer BASKET_PRICER_TWO_DAYS_HENCE = BasketPricerCreator.forDay(TWO_DAYS_HENCE);
    private static final BasketPricer BASKET_PRICER_THREE_DAYS_HENCE = BasketPricerCreator.forDay(THREE_DAYS_HENCE);
    private static final BasketPricer BASKET_PRICER_FIVE_DAYS_HENCE = BasketPricerCreator.forDay(FIVE_DAYS_HENCE);
    private static final BasketPricer BASKET_PRICER_END_OF_NEXT_MONTH = BasketPricerCreator.forDay(END_OF_NEXT_MONTH);
    private static final BasketPricer BASKET_PRICER_DAY_AFTER_END_OF_NEXT_MONTH = BasketPricerCreator.forDay(DAY_AFTER_END_OF_NEXT_MONTH);


    @Test
    void emptyBasket() {
        assertBasketValue(Basket.create().priceUp(BASKET_PRICER_TODAY), 0);
    }

    @Test
    void singleMilk() {
        assertBasketValue(Basket.create().add(1, MILK)
                                .priceUp(BASKET_PRICER_TODAY), 1.30);
    }

    @Test
    void multipleMilk() {
        assertBasketValue(Basket.create().add(3, MILK)
                                .priceUp(BASKET_PRICER_TODAY), 3.90);
    }

    @Test
    void differentItems() {
        assertBasketValue(Basket.create().add(1, MILK)
                                .add(1, APPLE)
                                .priceUp(BASKET_PRICER_TODAY), 1.40);
    }

    @Test
    void discountedApple() {
        assertBasketValue(Basket.create().add(1, APPLE)
                                .priceUp(BASKET_PRICER_THREE_DAYS_HENCE), 0.09);
        assertBasketValue(Basket.create()
                                .add(1, APPLE)
                                .priceUp(BASKET_PRICER_END_OF_NEXT_MONTH), 0.09);
    }

    @Test
    void fullPriceApples() {
        assertBasketValue(Basket.create().add(1, APPLE)
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
        assertBasketValue(Basket.create().add(1, BREAD)
                                .add(1, SOUP)
                                .priceUp(BASKET_PRICER_TODAY), 1.45);
        assertBasketValue(Basket.create()
                                .add(2, SOUP)
                                .priceUp(BASKET_PRICER_TODAY), 1.30);
    }

    @Test
    void halfPriceBread() {
        assertBasketValue(Basket.create()
                                .add(2, SOUP)
                                .add(1, BREAD)
                                .priceUp(BASKET_PRICER_TODAY), 1.70);
        assertBasketValue(Basket.create()
                                .add(4, SOUP)
                                .add(2, BREAD)
                                .priceUp(BASKET_PRICER_TODAY), 3.40);
        assertBasketValue(Basket.create()
                                .add(4, SOUP)
                                .add(1, BREAD)
                                .priceUp(BASKET_PRICER_TODAY), 3.00);
    }

    @Test
    void multiPromotions() {
        assertBasketValue(Basket.create()
                                .add(2, SOUP)
                                .add(1, BREAD)
                                .add(1, APPLE)
                                .priceUp(BASKET_PRICER_THREE_DAYS_HENCE), 1.79);

    }

    @Test
    void example1() {
        assertBasketValue(Basket.create()
                                .add(3, SOUP)
                                .add(2, BREAD)
                                .priceUp(BASKET_PRICER_TODAY), 3.15);
    }

    @Test
    void example2() {
        assertBasketValue(Basket.create()
                                .add(6, APPLE)
                                .add(1, MILK)
                                .priceUp(BASKET_PRICER_TODAY), 1.90);
    }

    @Test
    void example3() {
        assertBasketValue(Basket.create()
                                .add(6, APPLE)
                                .add(1, MILK)
                                .priceUp(BASKET_PRICER_FIVE_DAYS_HENCE), 1.84);
    }

    @Test
    void example4() {
        assertBasketValue(Basket.create()
                                .add(3, APPLE)
                                .add(2, SOUP)
                                .add(1, BREAD)
                                .priceUp(BASKET_PRICER_FIVE_DAYS_HENCE), 1.97);
    }

    private void assertBasketValue(BigDecimal total, double expected) {
        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(expected));
    }
}