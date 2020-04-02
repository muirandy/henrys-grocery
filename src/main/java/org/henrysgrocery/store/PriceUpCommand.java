package org.henrysgrocery.store;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;

class PriceUpCommand implements Command {
    private static final String PRICE_UP_MESSAGE = "--Total Basket Cost: ";

    private final CommandFactory commandFactory;
    private final PrintStream out;
    private final Basket basket;

    public PriceUpCommand(CommandFactory commandFactory, PrintStream out, Basket basket) {
        this.commandFactory = commandFactory;
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
        Matcher matcher = commandFactory.obtainPriceUpMatcher(command);
        matcher.find();
        String daysOffset = Optional.ofNullable(matcher.group(1)).orElse("0");
        return Long.parseLong(daysOffset.trim());
    }

    private void displayBasketTotal(BigDecimal total) {
        total = total.setScale(2, RoundingMode.CEILING);
        out.println(PRICE_UP_MESSAGE + total);
    }
}
