package org.henrysgrocery.store;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.henrysgrocery.store.ProductCatalog.*;
import static org.henrysgrocery.store.Unit.*;

class HenrysGroceryStoreShould {
    private static final ProductCatalog PRODUCT_CATALOG = createProductCatalog();
    private static final Item BOTTLE_MILK = PRODUCT_CATALOG.getItem(BOTTLE, MILK);
    private static final Item SINGLE_APPLE = PRODUCT_CATALOG.getItem(SINGLE, APPLES);
    private static final Item LOAF_BREAD = PRODUCT_CATALOG.getItem(LOAF, BREAD);
    private static final Item TIN_SOUP = PRODUCT_CATALOG.getItem(TIN, SOUP);

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TWO_DAYS_AGO = TODAY.minusDays(2);
    private static final LocalDate YESTERDAY = TODAY.minusDays(1);
    private static final LocalDate TWO_DAYS_HENCE = TODAY.plusDays(2);
    private static final LocalDate THREE_DAYS_HENCE = TODAY.plusDays(3);
    private static final LocalDate FIVE_DAYS_HENCE = TODAY.plusDays(5);
    private static final LocalDate ONE_WEEK_HENCE = TODAY.plusDays(7);
    private static final LocalDate END_OF_NEXT_MONTH = TODAY.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
    private static final LocalDate DAY_AFTER_END_OF_NEXT_MONTH = END_OF_NEXT_MONTH.plusDays(1);

    private static final BasketPricer BASKET_PRICER_TODAY = BasketPricerCreator.forDay(TODAY);
    private static final BasketPricer BASKET_PRICER_TWO_DAYS_AGO = BasketPricerCreator.forDay(TWO_DAYS_AGO);
    private static final BasketPricer BASKET_PRICER_YESTERDAY = BasketPricerCreator.forDay(YESTERDAY);
    private static final BasketPricer BASKET_PRICER_TWO_DAYS_HENCE = BasketPricerCreator.forDay(TWO_DAYS_HENCE);
    private static final BasketPricer BASKET_PRICER_THREE_DAYS_HENCE = BasketPricerCreator.forDay(THREE_DAYS_HENCE);
    private static final BasketPricer BASKET_PRICER_FIVE_DAYS_HENCE = BasketPricerCreator.forDay(FIVE_DAYS_HENCE);
    private static final BasketPricer BASKET_PRICER_ONE_WEEK_HENCE = BasketPricerCreator.forDay(ONE_WEEK_HENCE);
    private static final BasketPricer BASKET_PRICER_END_OF_NEXT_MONTH = BasketPricerCreator.forDay(END_OF_NEXT_MONTH);
    private static final BasketPricer BASKET_PRICER_DAY_AFTER_END_OF_NEXT_MONTH = BasketPricerCreator.forDay(DAY_AFTER_END_OF_NEXT_MONTH);


    @Test
    void emptyBasket() {
        assertBasketValue(Basket.create().priceUp(BASKET_PRICER_TODAY), 0);
    }

    @Test
    void singleMilk() {
        assertBasketValue(Basket.create().add(1, BOTTLE_MILK)
                                .priceUp(BASKET_PRICER_TODAY), 1.30);
    }

    @Test
    void multipleMilk() {
        assertBasketValue(Basket.create().add(3, BOTTLE_MILK)
                                .priceUp(BASKET_PRICER_TODAY), 3.90);
    }

    @Test
    void differentItems() {
        assertBasketValue(Basket.create().add(1, BOTTLE_MILK)
                                .add(1, SINGLE_APPLE)
                                .priceUp(BASKET_PRICER_TODAY), 1.40);
    }

    @Test
    void discountedApple() {
        assertBasketValue(Basket.create().add(1, SINGLE_APPLE)
                                .priceUp(BASKET_PRICER_THREE_DAYS_HENCE), 0.09);
        assertBasketValue(Basket.create()
                                .add(1, SINGLE_APPLE)
                                .priceUp(BASKET_PRICER_END_OF_NEXT_MONTH), 0.09);
    }

    @Test
    void fullPriceApples() {
        assertBasketValue(Basket.create().add(1, SINGLE_APPLE)
                                .priceUp(BASKET_PRICER_TODAY), 0.10);
        assertBasketValue(Basket.create()
                                .add(1, SINGLE_APPLE)
                                .priceUp(BASKET_PRICER_TWO_DAYS_HENCE), 0.10);
        assertBasketValue(Basket.create()
                                .add(1, SINGLE_APPLE)
                                .priceUp(BASKET_PRICER_DAY_AFTER_END_OF_NEXT_MONTH), 0.10);
    }

    @Test
    void fullPriceBread() {
        assertBasketValue(Basket.create()
                                .add(1, LOAF_BREAD)
                                .add(1, TIN_SOUP)
                                .priceUp(BASKET_PRICER_TODAY), 1.45);
        assertBasketValue(Basket.create()
                                .add(2, TIN_SOUP)
                                .priceUp(BASKET_PRICER_TODAY), 1.30);
        assertBasketValue(Basket.create()
                                .add(2, TIN_SOUP)
                                .add(1, LOAF_BREAD)
                                .priceUp(BASKET_PRICER_TWO_DAYS_AGO), 2.10);
        assertBasketValue(Basket.create()
                                .add(2, TIN_SOUP)
                                .add(1, LOAF_BREAD)
                                .priceUp(BASKET_PRICER_ONE_WEEK_HENCE), 2.10);
    }

    @Test
    void halfPriceBread() {
        assertBasketValue(Basket.create()
                                .add(2, TIN_SOUP)
                                .add(1, LOAF_BREAD)
                                .priceUp(BASKET_PRICER_TODAY), 1.70);
        assertBasketValue(Basket.create()
                                .add(4, TIN_SOUP)
                                .add(2, LOAF_BREAD)
                                .priceUp(BASKET_PRICER_TODAY), 3.40);
        assertBasketValue(Basket.create()
                                .add(4, TIN_SOUP)
                                .add(1, LOAF_BREAD)
                                .priceUp(BASKET_PRICER_TODAY), 3.00);
        assertBasketValue(Basket.create()
                                .add(2, TIN_SOUP)
                                .add(1, LOAF_BREAD)
                                .priceUp(BASKET_PRICER_YESTERDAY), 1.70);
    }

    @Test
    void multiPromotions() {
        assertBasketValue(Basket.create()
                                .add(2, TIN_SOUP)
                                .add(1, LOAF_BREAD)
                                .add(1, SINGLE_APPLE)
                                .priceUp(BASKET_PRICER_THREE_DAYS_HENCE), 1.79);
    }

    @Test
    void example1() {
        assertBasketValue(Basket.create()
                                .add(3, TIN_SOUP)
                                .add(2, LOAF_BREAD)
                                .priceUp(BASKET_PRICER_TODAY), 3.15);
    }

    @Test
    void example2() {
        assertBasketValue(Basket.create()
                                .add(6, SINGLE_APPLE)
                                .add(1, BOTTLE_MILK)
                                .priceUp(BASKET_PRICER_TODAY), 1.90);
    }

    @Test
    void example3() {
        assertBasketValue(Basket.create()
                                .add(6, SINGLE_APPLE)
                                .add(1, BOTTLE_MILK)
                                .priceUp(BASKET_PRICER_FIVE_DAYS_HENCE), 1.84);
    }

    @Test
    void example4() {
        assertBasketValue(Basket.create()
                                .add(3, SINGLE_APPLE)
                                .add(2, TIN_SOUP)
                                .add(1, LOAF_BREAD)
                                .priceUp(BASKET_PRICER_FIVE_DAYS_HENCE), 1.97);
    }

    private void assertBasketValue(BigDecimal total, double expected) {
        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(expected));
    }
}