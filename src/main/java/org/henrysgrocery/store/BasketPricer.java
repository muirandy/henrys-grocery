package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

class BasketPricer {

    public static BasketPricer forDay(LocalDate today) {
        BasketPricer basketPricer = new BasketPricer();
        if (applePromotionApplies(today))
            return basketPricer.withPromotion(new ApplePromotion());

        return basketPricer;
    }

    private static boolean applePromotionApplies(LocalDate purchaseDate) {
        LocalDate today = LocalDate.now();
        LocalDate dayAfterEndOfNextMonth = today.plusMonths(2).with(TemporalAdjusters.firstDayOfMonth());
        return purchaseDate.isAfter(today.plusDays(2))
                && purchaseDate.isBefore(dayAfterEndOfNextMonth);
    }

    private List<ApplePromotion> promotions = new ArrayList<>();

    private BasketPricer withPromotion(ApplePromotion applePromotion) {
        addPromotion(applePromotion);
        return this;
    }

    private void addPromotion(ApplePromotion applePromotion) {
        promotions.add(applePromotion);
    }

    public BigDecimal priceUp(List<Item> items) {
        BigDecimal total = items.stream()
                .map(i -> i.price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discount = calculateDiscount(items);
        return total.subtract(discount);
    }

    private BigDecimal calculateDiscount(List<Item> items) {
        return promotions.stream()
                  .map(p -> p.apply(items))
                  .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
