package org.henrysgrocery.store;

import java.time.LocalDate;

abstract class DateRangePromotion implements Promotion {
    protected final LocalDate startDate;
    protected final LocalDate endDate;

    DateRangePromotion(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean applies(LocalDate purchaseDate) {
        return purchaseDate.isAfter(startDate.minusDays(1))
                && purchaseDate.isBefore(endDate.plusDays(1));
    }
}
