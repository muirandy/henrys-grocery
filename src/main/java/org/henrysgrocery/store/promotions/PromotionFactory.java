package org.henrysgrocery.store.promotions;

import org.henrysgrocery.store.Item;
import org.henrysgrocery.store.ProductCatalog;
import org.henrysgrocery.store.Promotion;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.henrysgrocery.store.ProductCatalog.*;
import static org.henrysgrocery.store.Unit.*;

public class PromotionFactory {

    private static ProductCatalog productCatalog = createProductCatalog();

    public static Promotion createSoupAndBreadPromotion() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(1);
        LocalDate endDate = startDate.plusDays(7);
        Item triggerItem = productCatalog.getItem(TIN, SOUP);
        Item targetItem = productCatalog.getItem(LOAF, BREAD);
        Promotion fixedPercentagePromotion = new FixedPercentagePromotion(startDate, endDate, targetItem, 50);
        return new TriggerAndTargetPromotion(startDate, endDate, triggerItem, 2, targetItem, fixedPercentagePromotion);
    }

    public static Promotion createApplePromotion() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.plusDays(3);
        LocalDate endDate = today.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        Item targetItem = productCatalog.getItem(SINGLE, APPLES);
        return new FixedPercentagePromotion(startDate, endDate, targetItem, 10);
    }
}
