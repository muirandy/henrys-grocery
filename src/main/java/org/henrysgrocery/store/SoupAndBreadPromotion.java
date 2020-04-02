package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class SoupAndBreadPromotion implements Promotion {

    private static final double DISCOUNT_MULTIPLIER = 0.5;

    private final LocalDate startDate;
    private final LocalDate endDate;

    private ProductCatalog productCatalog = ProductCatalog.createProductCatalog();

    SoupAndBreadPromotion(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public BigDecimal apply(List<Item> items) {
        long numberOfSoups = items.stream()
                                  .filter(i -> i.name.equals(ProductCatalog.SOUP))
                                  .count();
        long promotionRepeats = numberOfSoups / 2;

        BigDecimal costWithoutDiscount = items.stream()
                                .filter(i -> i.name.equals(ProductCatalog.BREAD))
                                .limit(promotionRepeats)
                                .map(i -> productCatalog.getPrice(i))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return costWithoutDiscount.multiply(BigDecimal.valueOf(DISCOUNT_MULTIPLIER));
    }

    public boolean applies(LocalDate purchaseDate) {
        return purchaseDate.isAfter(startDate.minusDays(1))
                && purchaseDate.isBefore(endDate.plusDays(1));
    }
}
