package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

class BasketPricer {

    private List<ApplePromotion> promotions = new ArrayList<>();

    public static BasketPricer forDay(LocalDate today) {
        BasketPricer basketPricer = new BasketPricer();
        ApplePromotion applePromotion = new ApplePromotion();
        if (applePromotion.applies(today)) {
            return basketPricer.withPromotion(applePromotion);
        }

        return basketPricer;
    }

    private BasketPricer withPromotion(ApplePromotion applePromotion) {
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