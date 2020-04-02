package org.henrysgrocery.store.ui;

import org.henrysgrocery.store.Basket;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandFactory {

    static final Pattern PRICE_UP = Pattern.compile("price( [+-][0-9]+)?");
    static final Pattern ADD = Pattern.compile("add ([0-9]+) ([A-Za-z]+) ([A-Za-z]+)");
    private static final String USAGE = "HELP";
    private static final String CATALOG = "CATALOG";
    private PrintStream out;
    private Basket basket = Basket.create();

    public CommandFactory(PrintStream out) {
        this.out = out;
    }

    public Command create(String command) {
        if (isAddCommand(command))
            return createAddCommand();
        if (isPriceUpCommand(command))
            return createPriceUpCommand();
        if (isUsageCommand(command))
            return createUsageCommand();
        if (isCatalogCommand(command))
            return createCatalogCommand();
        return createInvalidCommand();
    }

    private boolean isAddCommand(String command) {
        Matcher matcher = ADD.matcher(command);
        return matcher.find();
    }

    private Command createAddCommand() {
        return new AddCommand(out, basket);
    }

    private boolean isPriceUpCommand(String command) {
        Matcher matcher = PRICE_UP.matcher(command);
        return matcher.find();
    }

    private Command createPriceUpCommand() {
        return new PriceUpCommand(out, basket);
    }

    private boolean isUsageCommand(String command) {
        return USAGE.equalsIgnoreCase(command);
    }

    private Command createUsageCommand() {
        return new UsageCommand(out);
    }

    private boolean isCatalogCommand(String command) {
        return CATALOG.equalsIgnoreCase(command);
    }

    private Command createCatalogCommand() {
        return new CatalogCommand(out);
    }

    private Command createInvalidCommand() {
        return new InvalidCommand(out);
    }
}