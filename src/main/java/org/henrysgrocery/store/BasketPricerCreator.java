package org.henrysgrocery.store;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.henrysgrocery.store.ProductCatalog.*;
import static org.henrysgrocery.store.Unit.*;

public class BasketPricerCreator {
    private static ProductCatalog productCatalog = createProductCatalog();

    public static BasketPricer forDay(LocalDate today) {
        List<Promotion> promotions = new ArrayList<>();
        Promotion applePromotion = createApplePromotion();
        if (applePromotion.applies(today)) {
            promotions.add(applePromotion);
        }

        Promotion soupAndBreadPromotion = createSoupAndBreadPromotion();
        if (soupAndBreadPromotion.applies(today))
            promotions.add(soupAndBreadPromotion);

        return new BasketPricer(promotions);
    }

    private static SoupAndBreadPromotion createSoupAndBreadPromotion() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(1);
        LocalDate endDate = startDate.plusDays(7);
        Item triggerItem = productCatalog.getItem(TIN, SOUP);
        Item targetItem = productCatalog.getItem(LOAF, BREAD);
        return new SoupAndBreadPromotion(startDate, endDate, triggerItem, 2, targetItem, 50);
    }

    private static FixedPercentagePromotion createApplePromotion() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.plusDays(3);
        LocalDate endDate = today.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        Item targetItem = productCatalog.getItem(SINGLE, APPLES);
        return new FixedPercentagePromotion(startDate, endDate, targetItem, 10);
    }
}