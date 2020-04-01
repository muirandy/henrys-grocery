package org.henrysgrocery.store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BasketPricerCreator {
    public static BasketPricer forDay(LocalDate today) {
        List<Promotion> promotions = new ArrayList<>();
        Promotion applePromotion = new ApplePromotion();
        if (applePromotion.applies(today)) {
            promotions.add(applePromotion);
        }

        Promotion soupAndBreadPromotion = new SoupAndBreadPromotion();
        if (soupAndBreadPromotion.applies(today))
            promotions.add(soupAndBreadPromotion);

        return new BasketPricer(promotions);
    }
}
