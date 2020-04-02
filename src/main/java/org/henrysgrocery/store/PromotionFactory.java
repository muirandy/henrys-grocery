package org.henrysgrocery.store;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.henrysgrocery.store.ProductCatalog.*;
import static org.henrysgrocery.store.Unit.*;

class PromotionFactory {

    private static ProductCatalog productCatalog = createProductCatalog();

    static SoupAndBreadPromotion createSoupAndBreadPromotion() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(1);
        LocalDate endDate = startDate.plusDays(7);
        Item triggerItem = productCatalog.getItem(TIN, SOUP);
        Item targetItem = productCatalog.getItem(LOAF, BREAD);
        return new SoupAndBreadPromotion(startDate, endDate, triggerItem, 2, targetItem, 50);
    }

    static FixedPercentagePromotion createApplePromotion() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.plusDays(3);
        LocalDate endDate = today.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        Item targetItem = productCatalog.getItem(SINGLE, APPLES);
        return new FixedPercentagePromotion(startDate, endDate, targetItem, 10);
    }
}
