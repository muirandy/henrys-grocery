package org.henrysgrocery.store;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CommandFactory {

    private static final String USAGE = "HELP";
    private static final String INVALID_ITEM = "--Invalid Item";

    static final Pattern ADD = Pattern.compile("add ([0-9]+) ([A-Za-z]+) ([A-Za-z]+)");
    private static final Pattern PRICE_UP = Pattern.compile("price( [+-][0-9]+)?");

    private PrintStream out;
    private Basket basket = Basket.create();

    public CommandFactory(PrintStream out) {
        this.out = out;
    }

    public void invoke(String command) {
        if (isAddCommand(command))
            createAddCommand().execute(command);
        else if (isPriceUpCommand(command))
            createPriceUpCommand(command).execute(command);
        else if (isUsageCommand(command))
            createUsageCommand().execute(command);
        else
            processInvalidCommand();
    }

    private boolean isAddCommand(String command) {
        Matcher matcher = ADD.matcher(command);
        return matcher.find();
    }

    private Command createAddCommand() {
        return new AddCommand(out, basket);
    }

    private boolean isPriceUpCommand(String command) {
        Matcher matcher = obtainPriceUpMatcher(command);
        return matcher.find();
    }

    private Command createPriceUpCommand(String command) {
        return new PriceUpCommand(this, out, basket);
    }

    private boolean isUsageCommand(String command) {
        return USAGE.equalsIgnoreCase(command);
    }

    private Command createUsageCommand() {
        return new UsageCommand(out);
    }

    private void processInvalidCommand() {
        out.println(INVALID_ITEM);
    }

    Matcher obtainPriceUpMatcher(String addCommand) {
        return PRICE_UP.matcher(addCommand);
    }


}
