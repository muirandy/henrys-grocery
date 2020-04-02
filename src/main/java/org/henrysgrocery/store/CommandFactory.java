package org.henrysgrocery.store;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CommandFactory {

    private static final String USAGE = "HELP";
    private static final String INVALID_ITEM = "--Invalid Item";

    static final Pattern ADD = Pattern.compile("add ([0-9]+) ([A-Za-z]+) ([A-Za-z]+)");
    private static final Pattern PRICE_UP = Pattern.compile("price( [+-][0-9]+)?");
    private static final String USAGE_MESSAGE =
            "Add an item to the basket:" + System.lineSeparator()
                    + "  add <quantity> <unit> <item>" + System.lineSeparator()
                    + "    eg: add 3 single apples" + System.lineSeparator()
                    + "Price up the basket:" + System.lineSeparator()
                    + "  price [+/- daysOffset]" + System.lineSeparator()
                    + "    eg: price" + System.lineSeparator()
                    + "    eg: price +5" + System.lineSeparator()
                    + "    eg: price -1" + System.lineSeparator()
                    + "Display this message:" + System.lineSeparator()
                    + "  usage" + System.lineSeparator()
                    + "Quit:" + System.lineSeparator()
                    + "  quit";

    private PrintStream out;
    private Basket basket = Basket.create();

    public CommandFactory(PrintStream out) {
        this.out = out;
    }

    public void invoke(String command) {
        if (isAddCommand(command))
            createAddCommand().execute(command);
        else if (isPriceUpCommand(command))
            processPriceUpCommand(command).execute(command);
        else if (isUsageCommand(command))
            processUsageCommand();
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

    private Command processPriceUpCommand(String command) {
        return new PriceUpCommand(this, out, basket);
    }

    private boolean isUsageCommand(String command) {
        return USAGE.equalsIgnoreCase(command);
    }

    private void processUsageCommand() {
        out.println(USAGE_MESSAGE);
    }

    private void processInvalidCommand() {
        out.println(INVALID_ITEM);
    }

    Matcher obtainPriceUpMatcher(String addCommand) {
        return PRICE_UP.matcher(addCommand);
    }


}
