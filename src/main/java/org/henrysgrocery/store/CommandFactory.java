package org.henrysgrocery.store;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CommandFactory {

    private static final String USAGE = "HELP";
    private static final String INVALID_ITEM = "--Invalid Item";
    private static final String ADD_TO_BASKET_MESSAGE = "--Added %d %s %s";
    private static final String PRICE_UP_MESSAGE = "--Total Basket Cost: ";
    private static final Pattern ADD = Pattern.compile("add ([0-9]+) ([A-Za-z]+) ([A-Za-z]+)");
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
    private ProductCatalog productCatalog = ProductCatalog.createProductCatalog();

    public CommandFactory(PrintStream out) {
        this.out = out;
    }

    public void invoke(String command) {
        if (isAddCommand(command))
            processAddCommand(command);
        else if (isPriceUpCommand(command))
            processPriceUpCommand(command);
        else if (isUsageCommand(command))
            processUsageCommand();
        else
            processInvalidCommand();
    }

    private boolean isAddCommand(String command) {
        Matcher matcher = obtainAddMatcher(command);
        return matcher.find();
    }

    private void processAddCommand(String addCommand) {
        try {
            addItemToBasket(addCommand);
        } catch (ProductCatalog.InvalidProductException | IllegalArgumentException e) {
            out.println(INVALID_ITEM);
        }
    }

    private void addItemToBasket(String addCommand) {
        Matcher matcher = prepareMatcher(addCommand);

        int quantity = readAddItemQuantity(matcher);
        String unit = readAddItemUnit(matcher);
        String itemName = readAddItemName(matcher);

        Item item = productCatalog.getItem(Unit.valueOf(unit), itemName);
        basket.add(quantity, item);
        acknowledgeItemAdded(quantity, item);
    }

    private String readAddItemName(Matcher matcher) {
        return matcher.group(3);
    }

    private String readAddItemUnit(Matcher matcher) {
        return matcher.group(2).toUpperCase();
    }

    private int readAddItemQuantity(Matcher matcher) {
        return Integer.parseInt(matcher.group(1));
    }

    private Matcher prepareMatcher(String addCommand) {
        Matcher matcher = obtainAddMatcher(addCommand);
        matcher.find();
        return matcher;
    }

    private Matcher obtainAddMatcher(String addCommand) {
        return ADD.matcher(addCommand);
    }

    private void acknowledgeItemAdded(int quantity, Item item) {
        String message = String.format(ADD_TO_BASKET_MESSAGE, quantity, item.unit.name().toLowerCase(), item.name);
        out.println(message);
    }

    private Matcher obtainPriceUpMatcher(String addCommand) {
        return PRICE_UP.matcher(addCommand);
    }

    private long readPurchaseDaysOffset(String command) {
        Matcher matcher = obtainPriceUpMatcher(command);
        matcher.find();
        String daysOffset = Optional.ofNullable(matcher.group(1)).orElse("0");
        return Long.parseLong(daysOffset.trim());
    }

    private void displayBasketTotal(BigDecimal total) {
        total = total.setScale(2, RoundingMode.CEILING);
        out.println(PRICE_UP_MESSAGE + total);
    }

    private boolean isPriceUpCommand(String command) {
        Matcher matcher = obtainPriceUpMatcher(command);
        return matcher.find();
    }

    private void processPriceUpCommand(String command) {
        long purchaseDaysOffset = readPurchaseDaysOffset(command);
        LocalDate purchaseDate = LocalDate.now().plusDays(purchaseDaysOffset);
        BigDecimal total = basket.priceUp(BasketPricerCreator.forDay(purchaseDate));
        displayBasketTotal(total);
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
}
