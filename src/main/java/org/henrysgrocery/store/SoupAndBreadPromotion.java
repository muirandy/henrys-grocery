package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SoupAndBreadPromotion implements Promotion {

    private static final double DISCOUNT_MULTIPLIER = 0.5;

    private ProductCatalog productCatalog = ProductCatalog.createProductCatalog();

    @Override
    public BigDecimal apply(List<Item> items) {
        long numberOfSoups = items.stream()
                                  .filter(i -> i.name.equals("soup"))
                                  .count();
        long promotionRepeats = numberOfSoups / 2;

        BigDecimal costWithoutDiscount = items.stream()
                                .filter(i -> i.name.equals("bread"))
                                .limit(promotionRepeats)
                                .map(i -> productCatalog.getPrice(i))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return costWithoutDiscount.multiply(BigDecimal.valueOf(DISCOUNT_MULTIPLIER));
    }

    public boolean applies(LocalDate purchaseDate) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(1);
        LocalDate endDate = startDate.plusDays(7);
        return true;
    }
}
