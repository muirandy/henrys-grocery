package org.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

class TriggerAndTargetPromotion extends DateRangePromotion {
    private Item triggerItem;
    private int triggerQuantity;
    private Item targetItem;
    private Promotion applicablePromotion;

    TriggerAndTargetPromotion(LocalDate startDate,
                              LocalDate endDate,
                              Item triggerItem,
                              int triggerQuantity,
                              Item targetItem,
                              Promotion applicablePromotion) {
        super(startDate, endDate);
        this.triggerItem = triggerItem;
        this.triggerQuantity = triggerQuantity;
        this.targetItem = targetItem;
        this.applicablePromotion = applicablePromotion;
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
        List<Item> itemsToDiscount = items.stream()
                                  .filter(i -> i.equals(targetItem))
                                  .limit(promotionRepeats)
                                  .collect(Collectors.toList());
        return applicablePromotion.calculateTotalDiscount(itemsToDiscount);
    }
}