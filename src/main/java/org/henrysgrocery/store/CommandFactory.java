package org.henrysgrocery.store;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CommandFactory {

    private static final String USAGE = "HELP";
    static final Pattern PRICE_UP = Pattern.compile("price( [+-][0-9]+)?");
    static final Pattern ADD = Pattern.compile("add ([0-9]+) ([A-Za-z]+) ([A-Za-z]+)");

    private PrintStream out;
    private Basket basket = Basket.create();

    public CommandFactory(PrintStream out) {
        this.out = out;
    }

    public Command create(String command) {
        if (isAddCommand(command))
            return createAddCommand();
        else if (isPriceUpCommand(command))
            return createPriceUpCommand();
        else if (isUsageCommand(command))
            return createUsageCommand();
        else
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

    private Command createInvalidCommand() {
        return new InvalidCommand(out);
    }
}