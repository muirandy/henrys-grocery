package org.henrysgrocery.store.ui;

import org.henrysgrocery.store.Basket;
import org.henrysgrocery.store.BasketPricerCreator;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.henrysgrocery.store.ui.CommandFactory.PRICE_UP;

class PriceUpCommand implements Command {
    private static final String PRICE_UP_MESSAGE = "--Total Basket Cost: ";

    private final PrintStream out;
    private final Basket basket;

    public PriceUpCommand(PrintStream out, Basket basket) {
        this.out = out;
        this.basket = basket;
    }

    @Override
    public void execute(String command) {
        long purchaseDaysOffset = readPurchaseDaysOffset(command);
        LocalDate purchaseDate = LocalDate.now().plusDays(purchaseDaysOffset);
        BigDecimal total = basket.priceUp(BasketPricerCreator.forDay(purchaseDate));
        displayBasketTotal(total);
    }

    private long readPurchaseDaysOffset(String command) {
        Matcher matcher = obtainPriceUpMatcher(command);
        matcher.find();
        String daysOffset = Optional.ofNullable(matcher.group(1)).orElse("0");
        return Long.parseLong(daysOffset.trim());
    }

    private Matcher obtainPriceUpMatcher(String addCommand) {
        return PRICE_UP.matcher(addCommand);
    }

    private void displayBasketTotal(BigDecimal total) {
        total = total.setScale(2, RoundingMode.CEILING);
        out.println(PRICE_UP_MESSAGE + total);
    }
}
