package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class SoupAndBreadPromotion extends DateRangePromotion {
    private static final BigDecimal ONE_HUNDREDTH = BigDecimal.valueOf(0.01);

    private ProductCatalog productCatalog = ProductCatalog.createProductCatalog();
    private Item triggerItem;
    private int triggerQuantity;
    private Item targetItem;
    private double targetPercentageDiscount;
    private BigDecimal discountMultiplier;

    SoupAndBreadPromotion(LocalDate startDate,
                          LocalDate endDate,
                          Item triggerItem,
                          int triggerQuantity,
                          Item targetItem,
                          double targetPercentageDiscount) {
        super(startDate, endDate);
        this.triggerItem = triggerItem;
        this.triggerQuantity = triggerQuantity;
        this.targetItem = targetItem;
        this.targetPercentageDiscount = targetPercentageDiscount;
        discountMultiplier = BigDecimal.valueOf(targetPercentageDiscount).multiply(ONE_HUNDREDTH);
    }

    @Override
    public BigDecimal calculateTotalDiscount(List<Item> items) {
        long numberOfSoups = items.stream()
                                  .filter(i -> i.equals(triggerItem))
                                  .count();
        long promotionRepeats = numberOfSoups / triggerQuantity;

        return calculateDiscountedAmount(items, promotionRepeats);
    }

    private BigDecimal calculateDiscountedAmount(List<Item> items, long promotionRepeats) {
        return items.stream()
                    .filter(i -> i.equals(targetItem))
                    .limit(promotionRepeats)
                    .map(i -> productCatalog.getPrice(i))
                    .map(p -> p.multiply(discountMultiplier))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}