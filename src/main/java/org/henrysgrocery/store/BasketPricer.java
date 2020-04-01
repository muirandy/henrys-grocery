package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

class BasketPricer {

    private List<Promotion> promotions = new ArrayList<>();

    public static BasketPricer forDay(LocalDate today) {
        BasketPricer basketPricer = new BasketPricer();
        Promotion applePromotion = new ApplePromotion();
        if (applePromotion.applies(today)) {
            return basketPricer.withPromotion(applePromotion);
        }

        Promotion soupAndBreadPromotion = new SoupAndBreadPromotion();
        if (soupAndBreadPromotion.applies(today))
            return basketPricer.withPromotion(soupAndBreadPromotion);

        return basketPricer;
    }

    private BasketPricer withPromotion(Promotion applePromotion) {
        promotions.add(applePromotion);
        return this;
    }

    public BigDecimal priceUp(List<Item> items) {
        BigDecimal baseCost = items.stream()
                                .map(i -> i.price)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discount = calculateDiscount(items);
        return baseCost.subtract(discount);
    }

    private BigDecimal calculateDiscount(List<Item> items) {
        return promotions.stream()
                         .map(p -> p.apply(items))
                         .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}